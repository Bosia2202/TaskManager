package com.interview.taskmanager.domain;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.taskmanager.task.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomTaskServiceTest {

    @Mock
    private TaskGateway taskGateway;

    @Mock
    private TaskAccessRightChecker taskAccessRightChecker;

    private CustomTaskService customTaskService;

    @BeforeEach
    void init() {
        customTaskService = new CustomTaskService(taskGateway,taskAccessRightChecker);
    }

    @Test
    void whenUseMethodUpdateTitleAndUserDoesNotPermissionToTask_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(taskAccessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer TASK_ID = 1;
        final String TASK_NEW_TITLE = "New title";
        final Integer CURRENT_USER_ID = 1;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> customTaskService.updateTitle(TASK_ID,TASK_NEW_TITLE, CURRENT_USER_ID));
    }

    @Test
    void whenUseMethodUpdateDescriptionAndUserDoesNotPermissionToTask_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(taskAccessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer TASK_ID = 1;
        final String TASK_NEW_DESCRIPTION = "New title";
        final Integer CURRENT_USER_ID = 1;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> customTaskService.updateDescription(TASK_ID,TASK_NEW_DESCRIPTION, CURRENT_USER_ID));
    }

    @Test
    void whenUseMethodUpdateStatusAndUserDoesNotPermissionToTask_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(taskAccessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer TASK_ID = 1;
        final TaskStatus TASK_NEW_STATUS = TaskStatus.COMPLETED;
        final Integer CURRENT_USER_ID = 1;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> customTaskService.updateStatus(TASK_ID,TASK_NEW_STATUS,CURRENT_USER_ID));
    }

    @Test
    void whenUseMethodUpdatePriorityAndUserDoesNotPermissionToTask_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(taskAccessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer TASK_ID = 1;
        final TaskPriority TASK_NEW_PRIORITY = TaskPriority.HIGH;
        final Integer CURRENT_USER_ID = 1;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> customTaskService.updatePriority(TASK_ID,TASK_NEW_PRIORITY,CURRENT_USER_ID));
    }

    @Test
    void whenUseMethodRemoveAndUserDoesNotPermissionToTask_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(taskAccessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer TASK_ID = 1;
        final Integer CURRENT_USER_ID = 1;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> customTaskService.remove(TASK_ID,CURRENT_USER_ID));
    }

}
