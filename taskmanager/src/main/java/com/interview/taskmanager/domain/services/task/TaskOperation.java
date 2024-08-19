package com.interview.taskmanager.domain.services.task;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.interview.taskmanager.adapters.database.CommentRepositoryAdapter;
import com.interview.taskmanager.adapters.database.TaskRepositoryAdapter;
import com.interview.taskmanager.adapters.database.UserRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.comment.CommentDto;
import com.interview.taskmanager.common.dto.profile.OwnerTaskDto;
import com.interview.taskmanager.common.dto.profile.OwnerTaskDtoMapper;
import com.interview.taskmanager.common.dto.profile.UserProfile;
import com.interview.taskmanager.common.dto.task.TaskBriefInfoDto;
import com.interview.taskmanager.common.dto.task.TaskBriefInfoDtoMapper;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.common.dto.task.TaskDto;

import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class TaskOperation implements TaskManagementService {

    private UserRepositoryAdapter userRepositoryAdapter;
    private TaskRepositoryAdapter taskRepositoryAdapter;
    private CommentRepositoryAdapter commentRepositoryAdapter;

    @Override
    public void createTask(TaskDetails taskDetails, String username) {
        log.info(String.format("Create new Task [author = '%s', title = '%s']", username,
                taskDetails.getTitle()));
        User user = userRepositoryAdapter.findByUsername(username);
        taskRepositoryAdapter.createNewTask(taskDetails, user);
    }

    @Override
    public void updateTaskById(Integer id, TaskDetails taskDetails, String username)
            throws NoResultException, AccessDeniedException {
        log.info(String.format("Updating task [id = %d] by user '%s'", id, username));
        if (taskRepositoryAdapter.isUserOwnerOfTask(username, id)) {
            taskRepositoryAdapter.updateTaskById(id, taskDetails);
        } else {
            throw new AccessDeniedException(String.format("Access denied. Task [id = '%d'] wasn't update", id));
        }
    }

    @Override
    public void removeTaskById(Integer taskId, String username)
            throws NoResultException, AccessDeniedException {
        log.info(String.format("Delete task [id = %d] by user = '%s'", taskId, username));
        if (taskRepositoryAdapter.isUserOwnerOfTask(username, taskId)) {
            taskRepositoryAdapter.removeTaskById(taskId);
        } else {
            throw new AccessDeniedException(String.format("Access denied. Task [id = '%d'] wasn't delete", taskId));
        }
    }

    @Override
    public void addExecutor(Integer taskId, Integer executorId, String username)
            throws NoResultException, AccessDeniedException {
        log.info(String.format("Add executor [id = %d] in task [id = '%d'] by user '%s'", executorId, taskId,
                username));
        User executor = userRepositoryAdapter.findById(executorId);
        if (taskRepositoryAdapter.isUserOwnerOfTask(username, taskId)) {
            taskRepositoryAdapter.addExecutorToTask(executor, taskId);
        } else {
            throw new AccessDeniedException(String.format(
                    "Access denied. Executor [id = '%d'] hasn't added in Task [id = '%d']", executorId, taskId));
        }

    }

    @Override
    public void removeExecutor(Integer taskId, Integer executorId, String username)
            throws NoResultException, AccessDeniedException {
        log.info(String.format("Delete executor [id = %d] in task [id = '%d'] by user '%s'", executorId, taskId,
                username));
        User executor = userRepositoryAdapter.findById(executorId);
        if (taskRepositoryAdapter.isUserOwnerOfTask(username, taskId)) {
            taskRepositoryAdapter.removeExecutorFromTask(executor, taskId);
        } else {
            throw new AccessDeniedException(String.format(
                    "Executor [id = '%d'] wasn't delete from task [id = '%d']. Access denied", executorId, taskId));
        }
    }

    @Override
    public TaskDto findById(Integer id) throws NoResultException {
        return taskRepositoryAdapter.loadCompleteTaskInfoById(id);
    }

    @Override
    public List<TaskBriefInfoDto> findAllTasksByTitle(String title, Integer pageNumber) {
        log.info(String.format("Searching tasks by title [title = %s]", title));
        return taskRepositoryAdapter.findTasksByTitle(title, pageNumber).stream().map(TaskBriefInfoDtoMapper::toDto)
                .toList();
    }

    @Override
    public List<OwnerTaskDto> getAssignedTasksList(String username) throws NoResultException {
        log.info(String.format("Get assigned tasks by user = '%s'", username));
        User user = userRepositoryAdapter.findUserWithAssignedTasksByUsername(username);
        return user.getOwnerTasks().stream().map(OwnerTaskDtoMapper::toDto)
                .toList();
    }

    @Override
    public void createComment(CommentDetails commentDetails, String username)
            throws NoResultException {
        log.info(String.format("Create new comment in task [id = %d] by user '%s'", commentDetails.getTaskId(),
                username));
        User author = userRepositoryAdapter.findByUsername(username);
        Task task = taskRepositoryAdapter.findById(commentDetails.getTaskId());
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setTask(task);
        comment.setDetails(commentDetails);
        commentRepositoryAdapter.createComment(comment);
    }

    @Override
    public void updateComment(Integer commentId, CommentDetails commentDetails, String username) throws AccessDeniedException {
        log.info(String.format("Update comment [id = %d] in task [id = %d], user '%s'", commentId,
                commentDetails.getTaskId(), username));
        if (commentRepositoryAdapter.isUsersComment(username, commentId)) {
            commentRepositoryAdapter.updateComment(commentId, commentDetails);
        } else {
            throw new AccessDeniedException(
                    String.format("Access denied. Comment [id = '%d'] wasn't updated", commentId));
        }
    }

    @Override
    public void removeComment(Integer commentId, String username) throws AccessDeniedException {
        log.info(String.format("Delete comment in comment [id = %d], user '%s'", commentId, username));
        if (commentRepositoryAdapter.isUsersComment(username, commentId)) {
            commentRepositoryAdapter.removeComment(commentId);
        } else {
            throw new AccessDeniedException(String.format("Comment [id = %d] wasn't delete. Access denied", commentId));
        }
    }

    @Override
    public List<CommentDto> getCommentsByTaskId(Integer taskId, Integer pageNumber) {
        return commentRepositoryAdapter.getCommentsByTaskId(taskId, pageNumber);
    }

    @Override
    public UserProfile getUserProfileById(Integer id) throws NoResultException {
        User user = userRepositoryAdapter.findById(id);
        List<Task> ownerTasks = taskRepositoryAdapter.getUserOwnedTasks(id);
        List<Task> executedTasks = taskRepositoryAdapter.getUserExecutedTasks(id);
        return new UserProfile(user, ownerTasks, executedTasks);
    }

    @Override
    public UserProfile getUserProfileByUsername(String username) throws NoResultException {
        User user = userRepositoryAdapter.findByUsername(username);
        List<Task> ownerTasks = taskRepositoryAdapter.getUserOwnedTasksByUsername(username);
        List<Task> executedTasks = taskRepositoryAdapter.getUserExecutedTasksByUsername(username);
        return new UserProfile(user, ownerTasks, executedTasks);
    }

}
