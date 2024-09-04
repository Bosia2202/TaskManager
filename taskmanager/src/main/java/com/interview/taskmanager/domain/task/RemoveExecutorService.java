package com.interview.taskmanager.domain.task;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.AccessRightChecker;
import com.interview.taskmanager.domain.security.IdentificationUserService;

public class RemoveExecutorService {

    private final TaskGateway taskGateway;

    private final IdentificationUserService identificationUserService;

    private final AccessRightChecker accessRightChecker;

    public RemoveExecutorService(TaskGateway taskGateway, IdentificationUserService identificationUserService, AccessRightChecker accessRightChecker) {
        this.taskGateway = taskGateway;
        this.identificationUserService = identificationUserService;
        this.accessRightChecker = accessRightChecker;
    }

    public void removeExecutor(Integer executorId, Integer taskId) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRightForRemovingExecutor(taskId, currentUserId);
        taskGateway.removeExecutor(executorId, taskId);
    }

    private void checkAccessRightForRemovingExecutor(Integer taskId, Integer currentUserId) {
        if (!accessRightChecker.isUserTask(taskId, currentUserId)) {
            String message = String.format("The executor cannot remove in task [Task = {id = '%d'}]. Access denied", taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }

}
