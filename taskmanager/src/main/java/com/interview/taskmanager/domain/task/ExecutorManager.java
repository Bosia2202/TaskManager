package com.interview.taskmanager.domain.task;

public interface ExecutorManager {

    void addExecutor(int taskId, int userId);

    void deleteExecutor(int taskId, int userId);
}
