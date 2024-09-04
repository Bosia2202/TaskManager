package com.interview.taskmanager.domain;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.taskmanager.task.TaskAccessRightChecker;
import com.interview.taskmanager.domain.taskmanager.task.TaskAssignmentService;
import com.interview.taskmanager.domain.taskmanager.task.TaskGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskAssignmentServiceTest {

    @Mock
    private TaskGateway taskGateway;

    @Mock
    private TaskAccessRightChecker taskAccessRightChecker;

    private TaskAssignmentService taskAssignmentService;

    @BeforeEach
    void init() {
        taskAssignmentService = new TaskAssignmentService(taskGateway, taskAccessRightChecker);
    }

    @Test
    void whenUseMethodAddExecutorAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(taskAccessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer EXECUTOR_ID = 1;
        final Integer TASK_ID = 1;
        final Integer CURRENT_USER_ID = 2;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> taskAssignmentService.addExecutor(EXECUTOR_ID, TASK_ID, CURRENT_USER_ID));
    }

    @Test
    void whenUseMethodRemoveExecutorAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(taskAccessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer EXECUTOR_ID = 1;
        final Integer TASK_ID = 1;
        final Integer CURRENT_USER_ID = 2;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> taskAssignmentService.removeExecutor(EXECUTOR_ID, TASK_ID, CURRENT_USER_ID));
    }
}
