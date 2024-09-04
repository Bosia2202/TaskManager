package com.interview.taskmanager.domain.task;

import com.interview.taskmanager.domain.security.IdentificationUserService;

public class CreateTaskService {

    private final TaskGateway taskGateway;

    private final IdentificationUserService identificationUserService;

    public CreateTaskService(TaskGateway taskGateway, IdentificationUserService identificationUserService) {
        this.taskGateway = taskGateway;
        this.identificationUserService = identificationUserService;
    }

    public void create(TaskDto taskDto) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        taskGateway.create(taskDto, currentUserId);
    }

}
