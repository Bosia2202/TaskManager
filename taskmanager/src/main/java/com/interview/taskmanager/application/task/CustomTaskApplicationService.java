package com.interview.taskmanager.application.task;

import com.interview.taskmanager.domain.taskmanager.task.CustomTaskService;
import com.interview.taskmanager.domain.taskmanager.task.TaskAccessRightChecker;
import com.interview.taskmanager.domain.taskmanager.task.TaskGateway;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomTaskApplicationService {

    private CustomTaskService customTaskService;

    @Autowired
    public CustomTaskApplicationService(TaskGateway taskGateway, TaskAccessRightChecker taskAccessRightChecker) {
        customTaskService = new CustomTaskService(taskGateway, taskAccessRightChecker);
    }

}
