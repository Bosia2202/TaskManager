package com.interview.taskmanager.domain.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.application.usecase.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.application.usecase.security.AccessRightChecker;
import com.interview.taskmanager.application.usecase.security.IdentificationUserService;
import com.interview.taskmanager.application.usecase.task.UpdateTaskService;
import com.interview.taskmanager.domain.TaskPriority;
import com.interview.taskmanager.domain.TaskStatus;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateTaskServiceTest {

    @Mock
    private TaskPort taskGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private AccessRightChecker accessRightChecker;

    private UpdateTaskService updateTaskService;

    @BeforeEach
    void init() {
        this.updateTaskService = new UpdateTaskService(taskGateway, identificationUserService, accessRightChecker);
    }

    @Test
    void whenUseMethodUpdateTitle_thanShouldNotGetRuntimeExceptions() {
        final Integer TASK_ID = 1;
        final String NEW_TASK_TITLE = "Title";
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> updateTaskService.updateTitle(TASK_ID, NEW_TASK_TITLE));
    }

    @Test
    void whenUseMethodUpdateTitleAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeException() {
        final Integer TASK_ID = 1;
        final String NEW_TASK_TITLE = "Title";
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> updateTaskService.updateTitle(TASK_ID, NEW_TASK_TITLE));
    }

    @Test
    void whenUseMethodUpdateDescription_thanShouldNotGetRuntimeExceptions() {
        final Integer TASK_ID = 1;
        final String NEW_TASK_DESCRIPTION = "Description";
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> updateTaskService.updateDescription(TASK_ID, NEW_TASK_DESCRIPTION));
    }

    @Test
    void whenUseMethodUpdateDescriptionAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeException() {
        final Integer TASK_ID = 1;
        final String NEW_TASK_DESCRIPTION = "Description";
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> updateTaskService.updateDescription(TASK_ID, NEW_TASK_DESCRIPTION));
    }

    @Test
    void whenUseMethodUpdateStatus_thanShouldNotGetRuntimeExceptions() {
        final Integer TASK_ID = 1;
        final TaskStatus NEW_TASK_STATUS = TaskStatus.COMPLETED;
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> updateTaskService.updateStatus(TASK_ID, NEW_TASK_STATUS));
    }

    @Test
    void whenUseMethodUpdateStatusAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeExceptions() {
        final Integer TASK_ID = 1;
        final TaskStatus NEW_TASK_STATUS = TaskStatus.COMPLETED;
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> updateTaskService.updateStatus(TASK_ID, NEW_TASK_STATUS));
    }

    @Test
    void whenUseMethodUpdatePriority_thanShouldNotGetRuntimeExceptions() {
        final Integer TASK_ID = 1;
        final TaskPriority NEW_TASK_PRIORITY = TaskPriority.MIDDLE;
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> updateTaskService.updatePriority(TASK_ID, NEW_TASK_PRIORITY));
    }

    @Test
    void whenUseMethodUpdatePriorityAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeException() {
        final Integer TASK_ID = 1;
        final TaskPriority NEW_TASK_PRIORITY = TaskPriority.MIDDLE;
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> updateTaskService.updatePriority(TASK_ID, NEW_TASK_PRIORITY));
    }

    
}
