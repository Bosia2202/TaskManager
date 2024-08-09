package com.interview.taskmanager.domain.services.task;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.CommentRepository;
import com.interview.taskmanager.adapters.database.repositories.TaskRepository;
import com.interview.taskmanager.adapters.database.repositories.UserRepository;
import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.TaskDetails;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TaskService implements TaskManagementService {
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private CommentRepository commentRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository,
            CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Task findByIdBriefInfo(int id) {
        log.info(String.format("Searching task brief information with id = '%d'", id));
        return taskRepository.findByIdBriefInfo(id);
    }

    @Override
    public Task findByIdWithAllInfo(int id) {
        log.info(String.format("Searching task all information with id = '%d'", id));
        return taskRepository.findByIdWithAllInfo(id);
    }

    @Override
    public List<Task> findAllTasksByTitleBriefInfo(String title) {
        log.info(String.format("Searching brief information for all tasks with title = %s", title));
        return taskRepository.findAllTasksByTitleBriefInfo(title);
    }

    @Override
    public List<Task> findAllTasksByTitleWithAllInfo(String title) {
        log.info(String.format("Searching all information for all tasks with title = %s", title));
        return taskRepository.findAllTasksByTitleWithAllInfo(title);
    }

    @Override
    public List<Task> getListAssignedTasks(Principal currentUser) {
        log.info(String.format("Get assigned tasks by user = '%s'", currentUser.getName()));
        User user = userRepository.findByUsernameWithTask(currentUser.getName());
        return user.getOwnerTasks();
    }

    @Override
    public void deleteTaskById(int taskId, Principal currentUser) {
        log.info(String.format("Delete task [id = %d] by user = '%s'", taskId, currentUser.getName()));
        User user = userRepository.findByUsernameWithTask(currentUser.getName());
        Task task = user.getOwnerTasks().stream().filter(t -> t.getId() == taskId).findFirst()
                .orElseThrow(() -> {
                    log.error(String.format("Task [id = '%d'] doesn't delete. Access denied", taskId));
                    throw new AccessDeniedException("Access denied");
                });
        taskRepository.deleteTask(task);
    }

    @Override
    public void updateTaskById(int taskId, TaskDetails taskDetails, Principal currentUser) {
        log.info(String.format("Update task [id = %d] by user '%s'", taskId, currentUser.getName()));
        User user = userRepository.findByUsernameWithTask(currentUser.getName());
        Task task = user.getOwnerTasks().stream().filter(t -> t.getId() == taskId).findFirst()
                .orElseThrow(() -> {
                    log.error(String.format("Task [id = '%d'] doesn't update. Access denied", taskId));
                    throw new AccessDeniedException("Access denied");
                });
        taskRepository.deleteTask(task);
    }

    @Override
    public void createTask(TaskDetails taskDetails, Principal currentUser) {
        log.info(String.format("Create new Task [author = '%s', title = '%s']", currentUser.getName(),
                taskDetails.getTitle()));
        User user = userRepository.findByUsername(currentUser.getName());
        taskRepository.createTask(taskDetails, user);
    }

    @Override
    public void createComment(int taskId, CommentDetails commentDetails, Principal currentUser) {
        log.info(String.format("Create new comment in task [id = %d], user '%s'", taskId, currentUser.getName()));
        Comment comment = new Comment();
        User author = userRepository.findByUsername(currentUser.getName());
        Task task = taskRepository.findByIdBriefInfo(taskId);
        comment.setAuthor(author);
        comment.setTask(task);
        commentRepository.createComment(comment);
    }

    @Override
    public void updateComment(int taskId, int commentId, CommentDetails commentDetails, Principal currentUser) {
        log.info(String.format("Update comment [id = %d] in task [id = %d], user '%s'", commentId, taskId,
                currentUser.getName()));
        User user = userRepository.findByUsernameWithComments(currentUser.getName());
        boolean checkRight = user.getComments().stream().anyMatch(comment -> comment.getId() == commentId);
        if (!checkRight) {
            log.error(String.format("Comment '%d' doesn't update. Access denied", commentId));
            throw new AccessDeniedException("Access denied");
        }
        commentRepository.updateComment(commentId, commentDetails);
    }

    @Override
    public void deleteComment(int commentId, Principal currentUser) {
        log.info(String.format("Delete comment in comment [id = %d], user '%s'", commentId, currentUser.getName()));
        User user = userRepository.findByUsernameWithComments(currentUser.getName());
        Comment comment = user.getComments().stream().filter(c -> c.getId() == commentId).findFirst()
                .orElseThrow(() -> {
                    log.error(String.format("Comment [id = %d] doesn't delete. Access denied", commentId));
                    throw new AccessDeniedException("Access denied");
                });
        commentRepository.deleteComment(comment);
    }

    @Override
    public void addExecutor(int taskId, int userId, Principal currentUser) {
        log.info(String.format("Add executor [id = %d] in task [id = '%d'] by user '%s'", userId, taskId,
                currentUser.getName()));
        User user = userRepository.findByUsernameWithTask(currentUser.getName());
        User executor = userRepository.findById(userId);
        Task task = user.getOwnerTasks().stream().filter(t -> t.getId() == taskId).findFirst()
                .orElseThrow(() -> {
                    log.error(String.format("Executer '%s' doesn't add. Access denied", executor.getUsername()));
                    throw new AccessDeniedException("Access denied");
                });
        taskRepository.addExecutor(executor, task);
    }

    @Override
    public void deleteExecutor(int taskId, int userId, Principal currentUser) {
        log.info(String.format("Delete executor [id = %d] in task [id = '%d'] by user '%s'", userId, taskId,
                currentUser.getName()));
        User user = userRepository.findByUsernameWithTask(currentUser.getName());
        User executor = userRepository.findById(userId);
        Task task = user.getOwnerTasks().stream().filter(t -> t.getId() == taskId).findFirst()
                .orElseThrow(() -> {
                    log.error(String.format("Executer '%s' doesn't delete. Access denied", executor.getUsername()));
                    throw new AccessDeniedException("Access denied");
                });
        taskRepository.deleteExecutor(executor, task);
    }

}
