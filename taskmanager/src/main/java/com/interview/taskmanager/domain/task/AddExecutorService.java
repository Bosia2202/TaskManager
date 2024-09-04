package com.interview.taskmanager.domain.task;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.AccessRightChecker;
import com.interview.taskmanager.domain.security.IdentificationUserService;

public class AddExecutorService {

    private final TaskGateway taskGateway;

    private final IdentificationUserService identificationUserService;

    private final AccessRightChecker accessRightChecker;

    public AddExecutorService(TaskGateway taskGateway, IdentificationUserService identificationUserService, AccessRightChecker accessRightChecker) {
        this.taskGateway = taskGateway;
        this.identificationUserService = identificationUserService;
        this.accessRightChecker = accessRightChecker;
    }

    public void addExecutor(Integer executorId, Integer taskId) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        taskGateway.addExecutor(executorId, taskId);
    }

    private void checkAccessRight(Integer taskId, Integer currentUserId) {
        if (!accessRightChecker.isUserTask(taskId, currentUserId)) {
            String message = String.format("The executor cannot add in task [Task = {id = '%d'}]. Access denied", taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }
}
