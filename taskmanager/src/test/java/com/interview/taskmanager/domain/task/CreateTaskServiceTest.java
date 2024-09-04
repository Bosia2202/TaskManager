package com.interview.taskmanager.domain.task;

import com.interview.taskmanager.domain.security.IdentificationUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTaskServiceTest {

    @Mock
    private TaskGateway taskGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    private CreateTaskService createTaskService;

    @BeforeEach
    void init() {
        this.createTaskService = new CreateTaskService(taskGateway, identificationUserService);
    }

    @Test
    void whenUseMethodCreate_thanShouldNotGetRuntimeException() {
        final String EXPECTED_TASK_TITLE = "Title";
        final String EXPECTED_TASK_DESCRIPTION = "Description";
        final TaskStatus EXPECTED_TASK_STATUS = TaskStatus.NOT_STARTED;
        final TaskPriority EXPECTED_TASK_PRIORITY = TaskPriority.LOWER;
        TaskDto taskDto = new TaskDto(EXPECTED_TASK_TITLE, EXPECTED_TASK_DESCRIPTION, EXPECTED_TASK_STATUS,
                EXPECTED_TASK_PRIORITY);
        Assertions.assertDoesNotThrow(() -> createTaskService.create(taskDto));
    }
}
