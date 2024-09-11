package com.interview.taskmanager.application.usecase.task;

import com.interview.taskmanager.application.dto.DatabaseTaskDto;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.domain.Task;
import com.interview.taskmanager.infra.exception.TaskAccessDeniedRuntimeException;
import com.interview.taskmanager.infra.exception.TaskNotFoundRuntimeException;

public class UpdateTaskService {

    private final TaskPort taskPort;

    private final SecurityPort securityPort;

    public UpdateTaskService(TaskPort taskPort, SecurityPort securityPort) {
        this.taskPort = taskPort;
        this.securityPort = securityPort;
    }

    public void updateTitle(String newTitle, Integer taskId) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        DatabaseTaskDto databaseTask = getTaskFromDatabaseById(taskId);
        Task task = new Task(databaseTask.id(), newTitle, databaseTask.description(), databaseTask.status(),
                databaseTask.priority(), databaseTask.authorId());
        taskPort.update(task);
    }

    private void checkAccessRight(Integer taskId, Integer userId) {
        Integer authorId = taskPort.getAuthorId(taskId);
        if (!userId.equals(authorId)) {
            String message = String.format("The task cannot be updated [Task = {id = '%d'}]. Access denied.", taskId);
            throw new TaskAccessDeniedRuntimeException(message);
        }
    }

    private DatabaseTaskDto getTaskFromDatabaseById(Integer taskId) {
        return taskPort.getTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundRuntimeException("Task with id = '%d' wasn't found"));
    }

    public void updateDescription(Integer taskId, String description) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        DatabaseTaskDto databaseTask = getTaskFromDatabaseById(taskId);
        Task task = new Task(databaseTask.id(), databaseTask.title(), description, databaseTask.status(),
                databaseTask.priority(), databaseTask.authorId());
        taskPort.update(task);
    }

    public void updateStatus(Integer taskId, String status) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        DatabaseTaskDto databaseTask = getTaskFromDatabaseById(taskId);
        Task task = new Task(databaseTask.id(), databaseTask.title(), databaseTask.description(), status,
                databaseTask.priority(), databaseTask.authorId());
        taskPort.update(task);
    }

    public void updatePriority(Integer taskId, String priority) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(taskId, currentUserId);
        DatabaseTaskDto databaseTask = getTaskFromDatabaseById(taskId);
        Task task = new Task(databaseTask.id(), databaseTask.title(), databaseTask.description(), databaseTask.status(),
                priority, databaseTask.authorId());
        taskPort.update(task);
    }

}
