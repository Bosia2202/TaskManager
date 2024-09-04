package com.interview.taskmanager.domain.task;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.AccessRightChecker;
import com.interview.taskmanager.domain.security.IdentificationUserService;

public class RemoveTaskService {

    private final TaskGateway taskGateway;
    private final IdentificationUserService identificationUserService;
    private final AccessRightChecker accessRightChecker;

    public RemoveTaskService(TaskGateway taskGateway, IdentificationUserService identificationUserService, AccessRightChecker accessRightChecker) {
        this.taskGateway = taskGateway;
        this.identificationUserService = identificationUserService;
        this.accessRightChecker = accessRightChecker;
    }

    public void remove(Integer taskId) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskGateway.remove(taskId);
    }

    private void checkAccessRight(Integer taskId, Integer userId) {
        if (!accessRightChecker.isUserTask(taskId, userId)) {
            String message = String.format("The task cannot be removed [Task = {id = '%d'}]. Access denied.", taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }

}
