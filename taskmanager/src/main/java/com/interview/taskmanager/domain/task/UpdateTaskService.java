package com.interview.taskmanager.domain.task;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.AccessRightChecker;
import com.interview.taskmanager.domain.security.IdentificationUserService;

public class UpdateTaskService {

    private final TaskGateway taskGateway;

    private final IdentificationUserService identificationUserService;

    private final AccessRightChecker accessRightChecker;

    public UpdateTaskService(TaskGateway taskGateway, IdentificationUserService identificationUserService, AccessRightChecker accessRightChecker) {
        this.taskGateway = taskGateway;
        this.identificationUserService = identificationUserService;
        this.accessRightChecker = accessRightChecker;
    }

    public void updateTitle(Integer taskId, String newTitle) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskGateway.updateTitle(taskId, newTitle);
    }

    private void checkAccessRight(Integer taskId, Integer userId) {
        if (!accessRightChecker.isUserTask(taskId, userId)) {
            String message = String.format("The task cannot be updated [Task = {id = '%d'}]. Access denied.", taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }

    public void updateDescription(Integer taskId, String description) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskGateway.updateDescription(taskId, description);
    }

    public void updateStatus(Integer taskId, TaskStatus status) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskGateway.updateStatus(taskId, status);
    }

    public void updatePriority(Integer taskId, TaskPriority priority) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskGateway.updatePriority(taskId, priority);
    }

}
