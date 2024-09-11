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
import com.interview.taskmanager.application.usecase.task.AddExecutorService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddExecutorServiceTest {

    @Mock
    private TaskPort taskGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private AccessRightChecker accessRightChecker;

    private AddExecutorService addExecutorService;

    @BeforeEach
    void init() {
        this.addExecutorService = new AddExecutorService(taskGateway, identificationUserService, accessRightChecker);
    }

    @Test
    void whenUseMethodAddExecutorAndUserHasPermission_thanShouldNotGetTaskAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(true);
        final Integer EXECUTOR_ID = 1;
        final Integer TASK_ID = 1;
        Assertions.assertDoesNotThrow(() -> addExecutorService.addExecutor(EXECUTOR_ID, TASK_ID));
    }

    @Test
    void whenUseMethodAddExecutorAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer EXECUTOR_ID = 1;
        final Integer TASK_ID = 1;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> addExecutorService.addExecutor(EXECUTOR_ID, TASK_ID));
    }
}
