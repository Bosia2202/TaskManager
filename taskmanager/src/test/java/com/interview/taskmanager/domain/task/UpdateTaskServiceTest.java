package com.interview.taskmanager.domain.task;

import com.interview.taskmanager.domain.security.AccessRightChecker;
import com.interview.taskmanager.domain.security.IdentificationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateTaskServiceTest {

    @Mock
    private TaskGateway taskGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private AccessRightChecker accessRightChecker;

    private UpdateTaskService updateTaskService;

    @BeforeEach
    void init() {

    }
}
