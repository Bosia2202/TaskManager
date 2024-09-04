package com.interview.taskmanager.domain.taskmanager.task;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;

public class TaskAssignmentService {

    private final TaskGateway taskGateway;

    private final TaskAccessRightChecker taskAccessRightChecker;

    public TaskAssignmentService(TaskGateway taskGateway, TaskAccessRightChecker taskAccessRightChecker) {
        this.taskGateway = taskGateway;
        this.taskAccessRightChecker = taskAccessRightChecker;
    }

    public void addExecutor(Integer executorId, Integer taskId, Integer currentUserId) {
        checkAccessRightForAddingExecutor(taskId, currentUserId);
        taskGateway.addExecutor(executorId, taskId);
    }

    private void checkAccessRightForAddingExecutor(Integer taskId, Integer currentUserId) {
        if (!taskAccessRightChecker.isUserTask(taskId, currentUserId)) {
            throw buildTaskAccessDeniedRuntimeExceptionForAddExecutor(taskId);
        }
    }

    private TaskAccessDeniedRuntimeException buildTaskAccessDeniedRuntimeExceptionForAddExecutor(Integer taskId) {
        String message = String.format("The executor cannot add in task [Task = {id = '%d'}]. Access denied", taskId);
        return new TaskAccessDeniedRuntimeException(message);
    }

    public void removeExecutor(Integer executorId, Integer taskId, Integer currentUserId) {
        checkAccessRightForRemovingExecutor(taskId, currentUserId);
        taskGateway.removeExecutor(executorId, taskId);
    }

    private void checkAccessRightForRemovingExecutor(Integer taskId, Integer currentUserId) {
        if (!taskAccessRightChecker.isUserTask(taskId, currentUserId)) {
            throw buildTaskAccessDeniedRuntimeExceptionForRemoveExecutor(taskId);
        }
    }

    private TaskAccessDeniedRuntimeException buildTaskAccessDeniedRuntimeExceptionForRemoveExecutor(Integer taskId) {
        String message = String.format("The executor cannot add in task [Task = {id = '%d'}]. Access denied", taskId);
        return new TaskAccessDeniedRuntimeException(message);
    }
}
