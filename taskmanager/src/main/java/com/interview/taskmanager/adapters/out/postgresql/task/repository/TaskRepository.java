package com.interview.taskmanager.adapters.out.postgresql.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.application.dto.DatabaseTaskDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Repository
public class TaskRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public void create(DatabaseTaskDto databaseTaskDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public Optional<DatabaseTaskDto> getTaskById(Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTaskById'");
    }

    public List<DatabaseTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTasksByTitle'");
    }

    public List<DatabaseTaskDto> getAllCustomTasksByAuthorId(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTasksByAuthorId'");
    }

    public List<DatabaseTaskDto> getAllExecuteTaskByUserId(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllExecuteTaskByUserId'");
    }

    public Integer getAuthorId(Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorId'");
    }

    public void update(DatabaseTaskDto databaseTaskDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public boolean remove(Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public boolean addExecutor(Integer executorId, Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addExecutor'");
    }

    public boolean removeExecutor(Integer executorId, Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeExecutor'");
    }

}
