package com.interview.taskmanager.adapters.database;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.common.dto.task.TaskDto;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface TaskRepositoryAdapter {

    Task findById(Integer id) throws EntityNotFoundException;

    List<Task> findAllByTitle(String title);

    void deleteById(Integer id) throws EntityNotFoundException;

    void updateById(Integer id, TaskDetails taskDetails) throws EntityNotFoundException;

    void create(TaskDetails taskDetails, User currentUser);

    void addExecutor(User executer, Task task);

    void deleteExecutor(Integer executerId, Integer taskId);

    List<Task> getOwnerTasksByUserId(Integer id);

    List<Task> getExecutedTasksByUserId(Integer id);

    List<Task> getOwnerTasksByUsername(String username);

    List<Task> getExecutedTasksByUsername(String username);

    TaskDto loadFullTaskInfoById(Integer id);

    boolean isUserOwnerOfTask(String username, Integer taskId);
}