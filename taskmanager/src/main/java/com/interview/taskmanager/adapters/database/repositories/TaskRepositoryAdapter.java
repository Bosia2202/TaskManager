package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.repositories.jpa.TaskJpaRepository;
import com.interview.taskmanager.common.dto.TaskDetails;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TaskRepositoryAdapter implements TaskRepository {
    private TaskJpaRepository repository;

    @Override
    public Task findByIdBriefInfo(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " doesn't found"));
    }

    @Override
    public List<Task> findAllTasksByTitleBriefInfo(String title) {
        return repository.findAllByTitle(title);
    }

    @Override
    public List<Task> findAllTasksByTitleWithAllInfo(String title) {
        return repository.findAllByTitle(title);
    }

    @Override
    public Task findByIdWithAllInfo(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " doesn't found"));
    }

    @Override
    public void deleteTaskById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void updateTaskById(int id, TaskDetails taskDetails) {
        Task task = findByIdBriefInfo(id);
        task.setDetails(taskDetails);
        repository.save(task);
    }

    @Override
    public void createTask(TaskDetails taskDetails) {
        Task task = new Task();
        task.setDetails(taskDetails);
        repository.save(task);
    }

}
