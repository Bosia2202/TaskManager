package com.interview.taskmanager.domain.services.task;

import java.security.Principal;
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
import com.interview.taskmanager.common.dto.profile.OwnerTaskDto;
import com.interview.taskmanager.common.dto.profile.OwnerTaskDtoMapper;
import com.interview.taskmanager.common.dto.profile.UserProfile;
import com.interview.taskmanager.common.dto.task.TaskBriefInfoDto;
import com.interview.taskmanager.common.dto.task.TaskBriefInfoDtoMapper;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.common.dto.task.TaskDto;
import com.interview.taskmanager.common.dto.task.TaskDtoMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TaskOperation implements TaskManagementService {

    private UserRepositoryAdapter userRepositoryAdapter;
    private TaskRepositoryAdapter taskRepositoryAdapter;
    private CommentRepositoryAdapter commentRepositoryAdapter;

    @Override
    public void createTask(TaskDetails taskDetails, Principal currentUser) {
        log.info(String.format("Create new Task [author = '%s', title = '%s']", currentUser.getName(),
                taskDetails.getTitle()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        taskRepositoryAdapter.create(taskDetails, user);
    }

    @Override
    public void updateTaskById(Integer id, TaskDetails taskDetails, Principal currentUser)
            throws EntityNotFoundException, AccessDeniedException {
        log.info(String.format("Update task [id = %d] by user '%s'", id, currentUser.getName()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        if (isUsersTask(user, id)) {
            taskRepositoryAdapter.updateById(id, taskDetails);
        } else {
            throw new AccessDeniedException(String.format("Access denied. Task [id = '%d'] wasn't update", id));
        }
    }

    @Override
    public void deleteTaskById(Integer taskId, Principal currentUser)
            throws EntityNotFoundException, AccessDeniedException {
        log.info(String.format("Delete task [id = %d] by user = '%s'", taskId, currentUser.getName()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        if (isUsersTask(user, taskId)) {
            taskRepositoryAdapter.deleteById(taskId);
        } else {
            throw new AccessDeniedException(String.format("Access denied. Task [id = '%d'] wasn't delete", taskId));
        }
    }

    @Override
    public void addExecutor(Integer taskId, Integer executorId, Principal currentUser)
            throws EntityNotFoundException, AccessDeniedException {
        log.info(String.format("Add executor [id = %d] in task [id = '%d'] by user '%s'", executorId, taskId,
                currentUser.getName()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        User executor = userRepositoryAdapter.findById(executorId);
        if (isUsersTask(user, taskId)) {
            taskRepositoryAdapter.addExecutorByTaskId(executor, taskId);
        } else {
            throw new AccessDeniedException(String.format(
                    "Access denied. Executor [id = '%d'] wasn't added in Task [id = '%d']", executorId, taskId));
        }
    }

    @Override
    public void deleteExecutor(Integer taskId, Integer executorId, Principal currentUser)
            throws EntityNotFoundException, AccessDeniedException {
        log.info(String.format("Delete executor [id = %d] in task [id = '%d'] by user '%s'", executorId, taskId,
                currentUser.getName()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        User executor = userRepositoryAdapter.findById(executorId);
        if (isUsersTask(user, taskId)) {
            taskRepositoryAdapter.addExecutorByTaskId(executor, executorId);
        } else {
            throw new AccessDeniedException(String.format(
                    "Access denied. Executor [id = '%d'] wasn't deleted from Task [id = '%d']", executorId, taskId));
        }
    }

    @Override
    public TaskDto findById(Integer id) throws EntityNotFoundException {
        log.info(String.format("Searching task [id = '%d']", id));
        return TaskDtoMapper.toDto(taskRepositoryAdapter.findById(id));
    }

    @Override
    public List<TaskBriefInfoDto> findAllTasksByTitle(String title) throws EntityNotFoundException {
        log.info(String.format("Searching tasks by title [title = %s]", title));
        return taskRepositoryAdapter.findAllByTitle(title).stream().map(TaskBriefInfoDtoMapper::toDto)
                .toList();
    }

    @Override
    public List<OwnerTaskDto> getAssignedTasksList(Principal currentUser) throws EntityNotFoundException {
        log.info(String.format("Get assigned tasks by user = '%s'", currentUser.getName()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        return user.getOwnerTasks().stream().map(OwnerTaskDtoMapper::toDto)
                .toList();
    }

    @Override
    public void createComment(CommentDetails commentDetails, Principal currentUser)
            throws EntityNotFoundException {
        log.info(String.format("Create new comment in task [id = %d] by user '%s'", commentDetails.getTaskId(),
                currentUser.getName()));
        User author = userRepositoryAdapter.findByUsername(currentUser.getName());
        Task task = taskRepositoryAdapter.findById(commentDetails.getTaskId());
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setTask(task);
        comment.setDetails(commentDetails);
        commentRepositoryAdapter.createComment(comment);
    }

    @Override
    public void updateComment(Integer commentId, CommentDetails commentDetails, Principal currentUser)
            throws AccessDeniedException {
        log.info(String.format("Update comment [id = %d] in task [id = %d], user '%s'", commentId,
                commentDetails.getTaskId(), currentUser.getName()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        if (!isUsersComment(user, commentId)) {
            commentRepositoryAdapter.updateComment(commentId, commentDetails);
        } else {
            throw new AccessDeniedException(
                    String.format("Access denied. Comment [id = '%d'] wasn't updated", commentId));
        }
    }

    @Override
    public void deleteComment(Integer commentId, Principal currentUser)
            throws EntityNotFoundException, AccessDeniedException {
        log.info(String.format("Delete comment in comment [id = %d], user '%s'", commentId, currentUser.getName()));
        User user = userRepositoryAdapter.findByUsername(currentUser.getName());
        if (isUsersComment(user, commentId)) {
            commentRepositoryAdapter.deleteComment(commentId);
        } else {
            throw new AccessDeniedException(String.format("Comment [id = %d] wasn't delete. Access denied", commentId));
        }
    }

    @Override
    public UserProfile getUserProfileById(Integer id) throws EntityNotFoundException {
       return userRepositoryAdapter.findUserProfileById(id);
    }

    @Override
    public UserProfile getUserProfileByUsername(String username) throws EntityNotFoundException {
        return userRepositoryAdapter.findUserProfileByUsername(username);   
    }

    private boolean isUsersTask(User user, Integer taskId) {
        return user.getOwnerTasks().stream().anyMatch(t -> t.getId().equals(taskId));
    }

    private boolean isUsersComment(User user, Integer commentId) {
        return user.getComments().stream().anyMatch(comment -> comment.getId().equals(commentId));
    }

}
