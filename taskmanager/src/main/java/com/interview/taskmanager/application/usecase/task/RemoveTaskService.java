package com.interview.taskmanager.application.usecase.task;

import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.infra.exception.TaskAccessDeniedRuntimeException;

public class RemoveTaskService {

    private final TaskPort taskPort;
    private final SecurityPort securityPort;

    public RemoveTaskService(TaskPort taskPort, SecurityPort securityPort) {
        this.taskPort = taskPort;
        this.securityPort = securityPort;
    }

    public void remove(Integer taskId) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskPort.remove(taskId);
    }

    private void checkAccessRight(Integer taskId, Integer userId) {
        Integer authorId = taskPort.getAuthorId(taskId);
        if (!userId.equals(authorId)) {
            String message = String.format("The task cannot be removed [Task = {id = '%d'}]. Access denied.", taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }

}
