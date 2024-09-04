package com.interview.taskmanager.domain.taskmanager.task;

import com.interview.taskmanager.domain.exception.TaskAccessDeniedRuntimeException;

import java.util.List;

public class CustomTaskService {

    private final TaskGateway taskGateway;
    private final TaskAccessRightChecker taskAccessRightChecker;

    public CustomTaskService(TaskGateway taskGateway, TaskAccessRightChecker taskAccessRightChecker) {
        this.taskGateway = taskGateway;
        this.taskAccessRightChecker = taskAccessRightChecker;
    }

    public void create(TaskDto taskDto, Integer authorId) {
        taskGateway.create(taskDto, authorId);
    }

    public void updateTitle(Integer taskId, String newTitle, Integer currentUserId) {
        checkAccessRight(taskId, currentUserId);
        taskGateway.updateTitle(taskId, newTitle);
    }

    private void checkAccessRight(Integer taskId, Integer userId) {
        if (!taskAccessRightChecker.isUserTask(taskId, userId)) {
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

    public void remove(Integer taskId, Integer userId) {
        checkAccessRight(taskId, userId);
        taskGateway.remove(taskId);
    }

    public TaskDto getCustomTaskById(Integer taskId) {
        return taskGateway.getCustomTask(taskId);
    }

    public List<BriefInformationTaskDto> getCustomTaskByTitle(String title, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return taskGateway.getCustomTasksByTitle(title, pageNumber, PAGE_SIZE);
    }

}
