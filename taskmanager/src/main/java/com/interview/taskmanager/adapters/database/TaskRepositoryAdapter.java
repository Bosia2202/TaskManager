package com.interview.taskmanager.adapters.database;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.task.TaskDetails;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface TaskRepositoryAdapter {
    
    Task findById(Integer id) throws EntityNotFoundException;

    List<Task> findAllByTitle(String title);

    void deleteById(Integer id) throws EntityNotFoundException;

    void updateById(Integer id, TaskDetails taskDetails) throws EntityNotFoundException;

    void create(TaskDetails taskDetails, User currentUser);

    void addExecutorByTaskId(User executer, Integer id) throws EntityNotFoundException;

    void deleteExecutorByTaskId(User executer, Integer id) throws EntityNotFoundException;

}