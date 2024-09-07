package com.interview.taskmanager.domain.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interview.taskmanager.domain.exception.TaskNotFoundRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchTaskServiceTest {

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
        final Integer EXPECTED_AUTHOR_ID = 1;
        final String EXPECTED_AUTHOR_USERNAME = "author";
        TaskAuthorDto taskAuthorDto = new TaskAuthorDto(EXPECTED_AUTHOR_ID, EXPECTED_AUTHOR_USERNAME);
        TaskPresentationDto taskDto = new TaskPresentationDto(EXPECTED_TASK_TITLE, EXPECTED_TASK_DESCRIPTION,
                EXPECTED_TASK_STATUS,
                EXPECTED_TASK_PRIORITY, taskAuthorDto);
        when(taskGateway.getTaskById(anyInt())).thenReturn(Optional.of(taskDto));
        final Integer TASK_ID = 1;
        TaskPresentationDto actualTask = searchTaskService.getTaskById(TASK_ID);
        Assertions.assertEquals(EXPECTED_TASK_TITLE, actualTask.title());
        Assertions.assertEquals(EXPECTED_TASK_DESCRIPTION, actualTask.description());
        Assertions.assertEquals(EXPECTED_TASK_STATUS, actualTask.status());
        Assertions.assertEquals(EXPECTED_TASK_PRIORITY, actualTask.priority());
        TaskAuthorDto actualAuthor = actualTask.author();
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualAuthor.id());
        Assertions.assertEquals(EXPECTED_AUTHOR_USERNAME, actualAuthor.username());
    }

    @Test
    void whenUseGetTaskByIdAndTaskDoesNotExist_thanShouldGetTaskNotFoundRuntimeException() {
        when(taskGateway.getTaskById(anyInt())).thenReturn(Optional.empty());
        final Integer TASK_ID = 1;
        Assertions.assertThrows(TaskNotFoundRuntimeException.class, () -> searchTaskService.getTaskById(TASK_ID));
    }

    @Test
    void whenUseMethodGetTaskByTitle_thanShouldGetExpectedTask() {
        final String EXPECTED_TASK_TITLE = "Title";
        final TaskStatus EXPECTED_TASK_STATUS = TaskStatus.NOT_STARTED;
        final TaskPriority EXPECTED_TASK_PRIORITY = TaskPriority.LOWER;
        final Integer EXPECTED_AUTHOR_ID = 1;
        final String EXPECTED_AUTHOR_USERNAME = "Author";
        TaskAuthorDto taskAuthorDto = new TaskAuthorDto(EXPECTED_AUTHOR_ID, EXPECTED_AUTHOR_USERNAME);
        BriefTaskDto briefTaskDto = new BriefTaskDto(EXPECTED_TASK_TITLE, EXPECTED_TASK_STATUS, EXPECTED_TASK_PRIORITY,
                taskAuthorDto);
        when(taskGateway.getTasksByTitle(anyString(), anyInt(), anyInt())).thenReturn(Collections
                .singletonList(briefTaskDto));
        final String TASK_TITLE = "Title";
        final Integer PAGE_NUMBER = 1;
        List<BriefTaskDto> tasks = searchTaskService.getTaskByTitle(TASK_TITLE, PAGE_NUMBER);
        BriefTaskDto actualBriefInfoTask = tasks.get(0);
        Assertions.assertEquals(EXPECTED_TASK_TITLE, actualBriefInfoTask.title());
        Assertions.assertEquals(EXPECTED_TASK_STATUS, actualBriefInfoTask.status());
        Assertions.assertEquals(EXPECTED_TASK_PRIORITY, actualBriefInfoTask.priority());
        TaskAuthorDto actualAuthor = actualBriefInfoTask.author();
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualAuthor.id());
        Assertions.assertEquals(EXPECTED_AUTHOR_USERNAME, actualAuthor.username());
    }
}
