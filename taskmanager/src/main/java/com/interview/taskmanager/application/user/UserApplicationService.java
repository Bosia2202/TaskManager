package com.interview.taskmanager.application.user;


import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.AccessRightChecker;
import com.interview.taskmanager.domain.task.TaskGateway;
import com.interview.taskmanager.domain.task.TaskPriority;
import com.interview.taskmanager.domain.task.TaskStatus;

public class UserApplicationService {

    private TaskGateway taskGateway;

    private AccessRightChecker accessRightChecker;

    public void updateTitle(Integer taskId, String newTitle, Integer currentUserId) {
        checkAccessRight(taskId, currentUserId);
        taskGateway.updateTitle(taskId, newTitle);
    }

    private void checkAccessRight(Integer taskId, Integer userId) {
        if (!accessRightChecker.isUserTask(taskId, userId)) {
            String message = String.format("The task cannot be updated [Task = {id = '%d'}]. Access denied.", taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }

    public void updateDescription(Integer taskId, String description, Integer userId) {
        checkAccessRight(taskId, userId);
        taskGateway.updateDescription(taskId, description);
    }

    public void updateStatus(Integer taskId, TaskStatus status, Integer userId) {
        checkAccessRight(taskId, userId);
        taskGateway.updateStatus(taskId, status);
    }

    public void updatePriority(Integer taskId, TaskPriority priority, Integer userId) {
        checkAccessRight(taskId, userId);
        taskGateway.updatePriority(taskId, priority);
    }

}
