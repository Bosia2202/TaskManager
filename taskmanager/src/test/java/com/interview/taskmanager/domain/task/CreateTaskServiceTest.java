package com.interview.taskmanager.domain.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interview.taskmanager.application.dto.NewTaskDto;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.application.usecase.security.IdentificationUserService;
import com.interview.taskmanager.application.usecase.task.CreateTaskService;
import com.interview.taskmanager.domain.TaskPriority;
import com.interview.taskmanager.domain.TaskStatus;

@ExtendWith(MockitoExtension.class)
public class CreateTaskServiceTest {

    @Mock
    private TaskPort taskGateway;

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
        NewTaskDto taskDto = new NewTaskDto(EXPECTED_TASK_TITLE, EXPECTED_TASK_DESCRIPTION, EXPECTED_TASK_STATUS,
                EXPECTED_TASK_PRIORITY);
        Assertions.assertDoesNotThrow(() -> createTaskService.create(taskDto));
    }
}
