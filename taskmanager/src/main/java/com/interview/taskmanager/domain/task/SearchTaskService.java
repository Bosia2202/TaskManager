package com.interview.taskmanager.domain.task;

import java.util.List;

public class SearchTaskService {

    private final TaskGateway taskGateway;

    public SearchTaskService(TaskGateway taskGateway) {
        this.taskGateway = taskGateway;
    }

    public TaskDto getTaskById(Integer taskId) {
        return taskGateway.getTaskById(taskId);
    }

    public List<BriefInformationTaskDto> getTaskByTitle(String title, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return taskGateway.getTasksByTitle(title, pageNumber, PAGE_SIZE);
    }

}
