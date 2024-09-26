package com.interview.taskmanager.application.usecase.task;

import com.interview.taskmanager.application.dto.NewTaskDto;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.domain.Task;
import com.interview.taskmanager.infra.exception.TaskCreateRuntimeException;

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
        try {
            taskPort.create(task);
        } catch (Exception e) {
            throw new TaskCreateRuntimeException(String.format("Task {title = %s, authorId = %d} didn't created. Exception %s", task.getTitle(), task.getAuthorId(), e.getMessage()));
        }
    }

}
