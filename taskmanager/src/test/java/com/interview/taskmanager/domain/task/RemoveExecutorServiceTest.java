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
import com.interview.taskmanager.application.usecase.task.RemoveExecutorService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveExecutorServiceTest {

    @Mock
    private TaskPort taskGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private AccessRightChecker accessRightChecker;

    private RemoveExecutorService removeExecutorService;

    @BeforeEach
    void init() {
        this.removeExecutorService = new RemoveExecutorService(taskGateway, identificationUserService, accessRightChecker);
    }

    @Test
    void whenUseMethodRemoveExecutorAndUserHasPermission_thanShouldNotGetTaskAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(true);
        final Integer EXECUTOR_ID = 1;
        final Integer TASK_ID = 1;
        Assertions.assertDoesNotThrow(() -> removeExecutorService.removeExecutor(EXECUTOR_ID, TASK_ID));
    }

    @Test
    void whenUseMethodRemoveExecutorAndUserDoesNotPermission_thanShouldGetTaskAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserTask(anyInt(), anyInt())).thenReturn(false);
        final Integer EXECUTOR_ID = 1;
        final Integer TASK_ID = 1;
        Assertions.assertThrows(TaskAccessDeniedRuntimeException.class, () -> removeExecutorService.removeExecutor(EXECUTOR_ID, TASK_ID));
    }


}
