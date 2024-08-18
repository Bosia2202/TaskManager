package com.interview.taskmanager.adapters.database;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.common.dto.task.TaskDto;

import jakarta.persistence.NoResultException;

@Repository
public interface TaskRepositoryAdapter {

    void createNewTask(TaskDetails taskDetails, User currentUser);

    void updateTaskById(Integer id, TaskDetails taskDetails) throws NoResultException;

    void removeTaskById(Integer id) throws NoResultException;

    TaskDto loadCompleteTaskInfoById(Integer id) throws NoResultException;

    List<Task> getUserOwnedTasks(Integer userId);

    List<Task> getUserExecutedTasks(Integer userId);

    List<Task> getUserOwnedTasksByUsername(String username);

    List<Task> getUserExecutedTasksByUsername(String username);

    boolean isUserOwnerOfTask(String username, Integer taskId);

    void addExecutorToTask(User executor, Integer taskId);

    void removeExecutorFromTask(User executor, Integer taskId) throws NoResultException;

    Task findById(Integer id) throws NoResultException;

    List<Task> findTasksByTitle(String title, Integer pageNumber);

}