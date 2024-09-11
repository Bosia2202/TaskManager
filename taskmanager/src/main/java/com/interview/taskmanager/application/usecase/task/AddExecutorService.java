package com.interview.taskmanager.application.usecase.task;

import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.infra.exception.TaskAccessDeniedRuntimeException;

public class AddExecutorService {

    private final TaskPort taskPort;

    private final SecurityPort securityPort;

    public AddExecutorService(TaskPort taskPort, SecurityPort securityPort) {
        this.taskPort = taskPort;
        this.securityPort = securityPort;
    }

    public void addExecutor(Integer executorId, Integer taskId) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskPort.addExecutor(executorId, taskId);
    }

    private void checkAccessRight(Integer taskId, Integer currentUserId) {
        Integer authorId = taskPort.getAuthorId(taskId);
        if (!currentUserId.equals(authorId)) {
            String message = String.format("The executor cannot add in task [Task = {id = '%d'}]. Access denied",
                    taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }
}
