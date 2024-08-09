package com.interview.taskmanager.adapters.database;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.TaskRepository;
import com.interview.taskmanager.adapters.database.repositories.jpa.TaskJpaRepository;
import com.interview.taskmanager.common.dto.TaskDetails;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TaskRepositoryAdapter implements TaskRepository {
    private TaskJpaRepository repository;

    @Override
    @EntityGraph(value = "task-entity-graph-with-brief-information")
    @Cacheable(value = "tasks", key = "#id")
    public Task findByIdBriefInfo(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " doesn't found"));
    }

    @Override
    @EntityGraph(value = "task-entity-graph-with-all-information")
    public List<Task> findAllTasksByTitleBriefInfo(String title) {
        return repository.findAllByTitle(title);
    }

    @Override
    @EntityGraph(value = "task-entity-graph-with-brief-information")
    public List<Task> findAllTasksByTitleWithAllInfo(String title) {
        return repository.findAllByTitle(title);
    }

    @Override
    @EntityGraph(value = "task-entity-graph-with-all-information")
    @Cacheable(value = "tasks", key = "#id")
    public Task findByIdWithAllInfo(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " doesn't found"));
    }

    @Override
    @Transactional
    @CacheEvict(value = "tasks", key = "#task.id")
    public void deleteTask(Task task) {
        repository.deleteById(task.getId());
    }

    @Override
    @Transactional
    @CachePut(value = "tasks", key = "#task.id")
    @CacheEvict(value = "tasks", key = "#task.id", beforeInvocation = true)
    public void updateTask(int taskId, TaskDetails taskDetails) {
        Task task = findByIdBriefInfo(taskId);
        task.setDetails(taskDetails);
        repository.save(task);
    }

    @Override
    public void createTask(TaskDetails taskDetails, User currentUser) {
        Task task = new Task();
        task.setDetails(taskDetails);
        task.setAuthor(currentUser);
        repository.save(task);
    }

    @Override
    public void addExecutor(User executer, Task task) {
        task.addExecutor(executer);
        repository.save(task);
    }

    @Override
    public void deleteExecutor(User executer, Task task) {
        task.deleteExecutor(executer);
        repository.save(task);
    }

}
