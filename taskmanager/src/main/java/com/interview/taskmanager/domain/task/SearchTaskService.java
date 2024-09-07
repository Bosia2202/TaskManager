package com.interview.taskmanager.domain.task;

import java.util.List;

import com.interview.taskmanager.domain.exception.TaskNotFoundRuntimeException;

public class SearchTaskService {

    private final TaskGateway taskGateway;

    public SearchTaskService(TaskGateway taskGateway) {
        this.taskGateway = taskGateway;
    }

    public TaskPresentationDto getTaskById(Integer taskId) {
        return taskGateway.getTaskById(taskId).orElseThrow(() -> new TaskNotFoundRuntimeException(
                String.format("Task with id = '%d' wasn't found.", taskId)));
    }

    public List<BriefTaskDto> getTaskByTitle(String title, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return taskGateway.getTasksByTitle(title, pageNumber, PAGE_SIZE);
    }

}
