package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.TaskRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.jpa.TaskJpaRepository;
import com.interview.taskmanager.common.dto.task.TaskDetails;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TaskRepository implements TaskRepositoryAdapter {

    private TaskJpaRepository taskJpaRepository;

    private CacheManager cacheManager;

    @Override
    @Transactional
    public void create(TaskDetails taskDetails, User currentUser) {
        Task task = new Task();
        task.setDetails(taskDetails);
        task.setAuthor(currentUser);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void updateById(Integer id, TaskDetails taskDetails) throws EntityNotFoundException {
        Task task = taskJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task [id = '%d'] wasn't found", id)));
        cacheManager.getCache("tasks").evictIfPresent(id);
        task.setDetails(taskDetails);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        cacheManager.getCache("tasks").evictIfPresent(id);
        taskJpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addExecutorByTaskId(User executer, Integer id) throws EntityNotFoundException {
        Task task = taskJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Task [id = '%d'] wasn't found. The executer has not been added", id)));
        task.addExecutor(executer);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteExecutorByTaskId(User executer, Integer id) throws EntityNotFoundException {
        Task task = taskJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Task [id = '%d'] wasn't found. The executer has not been deleted", id)));
        task.deleteExecutor(executer);
        taskJpaRepository.save(task);
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    @EntityGraph(value = "task-entity-graph")
    @Transactional(readOnly = true)
    public Task findById(Integer id) throws EntityNotFoundException {
        return taskJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task [id = '%d'] wasn't found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllByTitle(String title) {
        return taskJpaRepository.findAllByTitle(title);
    }

}
