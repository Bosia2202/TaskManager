package com.interview.taskmanager.domain.task;

import java.util.List;

public class SearchTaskService {

    private final TaskGateway taskGateway;

    public SearchTaskService(TaskGateway taskGateway) {
        this.taskGateway = taskGateway;
    }

    public TaskDto getCustomTaskById(Integer taskId) {
        return taskGateway.getCustomTask(taskId);
    }

    public List<BriefInformationTaskDto> getCustomTaskByTitle(String title, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return taskGateway.getCustomTasksByTitle(title, pageNumber, PAGE_SIZE);
    }

}
