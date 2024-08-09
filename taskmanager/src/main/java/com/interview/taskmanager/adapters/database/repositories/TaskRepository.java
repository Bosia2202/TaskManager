package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.TaskDetails;

public interface TaskRepository {
    Task findByIdBriefInfo(int id);

    Task findByIdWithAllInfo(int id);

    public List<Task> findAllTasksByTitleBriefInfo(String title);

    public List<Task> findAllTasksByTitleWithAllInfo(String title);

    void deleteTask(Task task);

    void updateTask(int taskId, TaskDetails taskDetails);

    void createTask(TaskDetails taskDetails, User currentUser);

    void addExecutor(User executer, Task task);

    void deleteExecutor(User executer, Task task);

}
