package com.interview.taskmanager.application.usecase.search;

import java.util.List;

import com.interview.taskmanager.application.dto.DatabaseTaskDto;
import com.interview.taskmanager.application.dto.TaskDto;
import com.interview.taskmanager.application.dto.TaskPreviewDto;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.infra.exception.TaskNotFoundRuntimeException;

public class SearchTaskService {

    private final UserPort userPort;
    private final TaskPort taskPort;

    public SearchTaskService(UserPort userPort, TaskPort taskPort) {
        this.userPort = userPort;
        this.taskPort = taskPort;
    }

    public TaskDto getTaskById(Integer taskId) {
        DatabaseTaskDto databaseTask = taskPort.getTaskById(taskId).orElseThrow(
                () -> new TaskNotFoundRuntimeException(String.format("Task with id = '%d' wasn't found.", taskId)));
        String authorUsername = userPort.getUsernameById(databaseTask.authorId());
        return new TaskDto(taskId, databaseTask.title(), databaseTask.description(), databaseTask.status().toString(),
                databaseTask.priority().toString(), databaseTask.authorId(), authorUsername);
    }

    public List<TaskPreviewDto> getTaskByTitle(String title, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        List<DatabaseTaskDto> databaseTasks = taskPort.getTasksByTitle(title, pageNumber, PAGE_SIZE);
        return databaseTasks
                .stream().map(task -> new TaskPreviewDto(task.title(), task.status().toString(),
                        task.priority().toString(), task.authorId(), userPort.getUsernameById(task.authorId())))
                .toList();
    }

}