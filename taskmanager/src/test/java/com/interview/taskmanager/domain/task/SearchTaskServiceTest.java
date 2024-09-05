package com.interview.taskmanager.domain.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchTaskServiceTest {

    @Mock
    private TaskGateway taskGateway;

    private SearchTaskService searchTaskService;

    @BeforeEach
    void init() {
        this.searchTaskService = new SearchTaskService(taskGateway);
    }

    @Test
    void whenUseGetTaskById_thanShouldGetExpectedTask() {
        final String EXPECTED_TASK_TITLE = "Title";
        final String EXPECTED_TASK_DESCRIPTION = "Description";
        final TaskStatus EXPECTED_TASK_STATUS = TaskStatus.NOT_STARTED;
        final TaskPriority EXPECTED_TASK_PRIORITY = TaskPriority.LOWER;
        TaskDto taskDto = new TaskDto(EXPECTED_TASK_TITLE, EXPECTED_TASK_DESCRIPTION, EXPECTED_TASK_STATUS,
                EXPECTED_TASK_PRIORITY);
        when(taskGateway.getTaskById(anyInt())).thenReturn(taskDto);
        final Integer TASK_ID = 1;
        TaskDto actualTask = searchTaskService.getTaskById(TASK_ID);
        Assertions.assertEquals(EXPECTED_TASK_TITLE, actualTask.title());
        Assertions.assertEquals(EXPECTED_TASK_DESCRIPTION, actualTask.description());
        Assertions.assertEquals(EXPECTED_TASK_STATUS, actualTask.status());
        Assertions.assertEquals(EXPECTED_TASK_PRIORITY, actualTask.priority());
    }

    @Test
    void whenUseMethodGetTaskByTitle_thanShouldGetExpectedTask() {
        final String EXPECTED_TASK_TITLE = "Title";
        final String EXPECTED_AUTHOR_USERNAME = "Author";
        final TaskStatus EXPECTED_TASK_STATUS = TaskStatus.NOT_STARTED;
        final TaskPriority EXPECTED_TASK_PRIORITY = TaskPriority.LOWER;
        BriefInformationTaskDto briefInformationTaskDto = new BriefInformationTaskDto(EXPECTED_TASK_TITLE, EXPECTED_AUTHOR_USERNAME, EXPECTED_TASK_STATUS,
                EXPECTED_TASK_PRIORITY);
        when(taskGateway.getTasksByTitle(anyString(), anyInt(), anyInt())).thenReturn(Collections
                .singletonList(briefInformationTaskDto));
        final String TASK_TITLE = "Title";
        final Integer PAGE_NUMBER = 1;
        List<BriefInformationTaskDto> tasks = searchTaskService.getTaskByTitle(TASK_TITLE, PAGE_NUMBER);
        BriefInformationTaskDto actualBriefInfoTask = tasks.get(0);
        Assertions.assertEquals(EXPECTED_TASK_TITLE, actualBriefInfoTask.title());
        Assertions.assertEquals(EXPECTED_AUTHOR_USERNAME, actualBriefInfoTask.username());
        Assertions.assertEquals(EXPECTED_TASK_STATUS, actualBriefInfoTask.status());
        Assertions.assertEquals(EXPECTED_TASK_PRIORITY, actualBriefInfoTask.priority());
    }
}
