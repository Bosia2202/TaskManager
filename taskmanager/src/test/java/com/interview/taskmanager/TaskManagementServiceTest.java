package com.interview.taskmanager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ActiveProfiles;

import com.interview.taskmanager.adapters.database.CommentRepositoryAdapter;
import com.interview.taskmanager.adapters.database.TaskRepositoryAdapter;
import com.interview.taskmanager.adapters.database.UserRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;
import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.domain.services.task.TaskManagementService;
import com.interview.taskmanager.domain.services.task.TaskOperation;

import jakarta.persistence.NoResultException;

@SpringBootTest
@ActiveProfiles("test")
class TaskManagementServiceTest {

    @Mock
    private CommentRepositoryAdapter commentRepositoryAdapter;

    @Mock
    private TaskRepositoryAdapter taskRepositoryAdapter;

    @Mock
    private UserRepositoryAdapter userRepositoryAdapter;

    private TaskManagementService taskManagementService;

    @BeforeEach
    void init() {
        this.taskManagementService = new TaskOperation(userRepositoryAdapter, taskRepositoryAdapter,
                commentRepositoryAdapter);
    }

    @Test
    void updateTaskById_whenIsUserOwnerOfTaskFalse_shouldThrowAccessDeniedException() {
        Integer taskId = 1;
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setTitle("title");
        taskDetails.setDescription("description");
        taskDetails.setPriority(TaskPriority.COMPLETE);
        taskDetails.setStatus(TaskStatus.HIGHT);
        String username = "testUser";
        when(taskRepositoryAdapter.isUserOwnerOfTask(anyString(), anyInt())).thenReturn(false);
        assertThrows(AccessDeniedException.class, () -> taskManagementService.updateTaskById(taskId,
                taskDetails, username));
    }

    @Test
    void updateTaskById_whenTaskDoesNotExist_shouldThrowNoResultException() {
        Integer taskId = 1;
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setTitle("title");
        taskDetails.setDescription("description");
        taskDetails.setPriority(TaskPriority.COMPLETE);
        taskDetails.setStatus(TaskStatus.HIGHT);
        String username = "testUser";
        when(taskRepositoryAdapter.isUserOwnerOfTask(anyString(), anyInt())).thenReturn(true);
        doThrow(NoResultException.class).when(taskRepositoryAdapter).updateTaskById(taskId, taskDetails);
        assertThrows(NoResultException.class, () -> taskManagementService.updateTaskById(taskId,
                taskDetails, username));
    }

    @Test
    void removeTaskById_whenIsUserOwnerOfTaskFalse_shouldThrowAccessDeniedException() {
        Integer taskId = 1;
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setTitle("title");
        taskDetails.setDescription("description");
        taskDetails.setPriority(TaskPriority.COMPLETE);
        taskDetails.setStatus(TaskStatus.HIGHT);
        String username = "testUser";
        when(taskRepositoryAdapter.isUserOwnerOfTask(anyString(), anyInt())).thenReturn(false);
        assertThrows(AccessDeniedException.class, () -> taskManagementService.removeTaskById(taskId, username));
    }

    @Test
    void removeTaskById_whenTaskDoesNotExist_shouldThrowNoResultException() {
        Integer taskId = 1;
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setTitle("title");
        taskDetails.setDescription("description");
        taskDetails.setPriority(TaskPriority.COMPLETE);
        taskDetails.setStatus(TaskStatus.HIGHT);
        String username = "testUser";
        when(taskRepositoryAdapter.isUserOwnerOfTask(anyString(), anyInt())).thenReturn(true);
        doThrow(NoResultException.class).when(taskRepositoryAdapter).removeTaskById(taskId);
        assertThrows(NoResultException.class, () -> taskManagementService.removeTaskById(taskId, username));
    }

    @Test
    void addExecutor_whenIsUserOwnerOfTaskFalse_shouldThrowAccessDenied() {
        Integer taskId = 1;
        String username = "testUser";
        Integer executorId = 1;
        when(taskRepositoryAdapter.isUserOwnerOfTask(username, taskId)).thenReturn(false);
        assertThrows(AccessDeniedException.class,
                () -> taskManagementService.addExecutor(taskId, executorId, username));
    }

    @Test
    void addExecutor_whenUserDoesNotExit_shouldThrowNoResultException() {
        Integer taskId = 1;
        String username = "testUser";
        Integer executorId = 1;
        doThrow(NoResultException.class).when(userRepositoryAdapter).findById(executorId);
        assertThrows(NoResultException.class, () -> taskManagementService.addExecutor(taskId, executorId, username));
    }

    @Test
    void removeExecutor_whenIsUserOwnerOfTaskFalse_shouldThrowAccessDenied() {
        Integer taskId = 1;
        String username = "testUser";
        Integer executorId = 1;
        when(taskRepositoryAdapter.isUserOwnerOfTask(username, taskId)).thenReturn(false);
        assertThrows(AccessDeniedException.class,
                () -> taskManagementService.removeExecutor(taskId, executorId, username));
    }

    @Test
    void removeExecutor_whenUserDoesNotExit_shouldThrowNoResultException() {
        Integer taskId = 1;
        String username = "testUser";
        Integer executorId = 1;
        doThrow(NoResultException.class).when(userRepositoryAdapter).findById(executorId);
        assertThrows(NoResultException.class,
                () -> taskManagementService.removeExecutor(taskId, executorId, username));
    }

    @Test
    void findById_whenTaskDoesNotExist_shouldThrowNoResultException() {
        Integer taskId = 1;
        doThrow(NoResultException.class).when(taskRepositoryAdapter).loadCompleteTaskInfoById(taskId);
        assertThrows(NoResultException.class, () -> taskManagementService.findById(taskId));
    }

    @Test
    void createComment_whenAuthorDoesNotExist_shouldThrowNoResultException() {
        Integer taskId = 1;
        String content = "content";
        CommentDetails commentDetails = new CommentDetails(taskId, content);
        String username = "testUser";
        doThrow(NoResultException.class).when(userRepositoryAdapter).findByUsername(username);
        assertThrows(NoResultException.class, () -> taskManagementService.createComment(commentDetails, username));
    }

    @Test
    void createComment_whenTaskDoesNotExist_shouldThrowNoResultException() {
        Integer taskId = 1;
        String content = "content";
        CommentDetails commentDetails = new CommentDetails(taskId, content);
        String username = "testUser";
        doThrow(NoResultException.class).when(taskRepositoryAdapter).findById(taskId);
        assertThrows(NoResultException.class, () -> taskManagementService.createComment(commentDetails, username));
    }

    @Test
    void updateComment_whenIsUsersCommentFalse_shouldThrowNoResultException() {
        Integer taskId = 1;
        String content = "content";
        CommentDetails commentDetails = new CommentDetails(taskId, content);
        String username = "testUser";
        Integer commentId = 1;
        when(commentRepositoryAdapter.isUsersComment(username, commentId)).thenReturn(false);
        assertThrows(AccessDeniedException.class,
                () -> taskManagementService.updateComment(commentId, commentDetails, username));
    }

    @Test
    void removeComment_whenIsUsersCommentFalse_shouldThrowNoResultException() {
        String username = "testUser";
        Integer commentId = 1;
        when(commentRepositoryAdapter.isUsersComment(username, commentId)).thenReturn(false);
        assertThrows(AccessDeniedException.class, () -> taskManagementService.removeComment(commentId, username));
    }

}
