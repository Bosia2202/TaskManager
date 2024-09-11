package com.interview.taskmanager.application.usecase.task;

import com.interview.taskmanager.application.dto.NewTaskDto;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.domain.Task;

public class CreateTaskService {

    private final TaskPort taskPort;

    private final SecurityPort securityPort;

    public CreateTaskService(TaskPort taskPort, SecurityPort securityPort) {
        this.taskPort = taskPort;
        this.securityPort = securityPort;
    }

    public void create(NewTaskDto newTaskDto) {
        Integer currentUserId = securityPort.getCurrentUserId();
        Task task = new Task(newTaskDto.title(),newTaskDto.description(), newTaskDto.status(), newTaskDto.priority(), currentUserId);
        taskPort.create(task);
    }

}
