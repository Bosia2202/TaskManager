package com.interview.taskmanager.adapters.out.postgresql.task;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.adapters.out.postgresql.task.repository.TaskRepository;
import com.interview.taskmanager.application.dto.DatabaseTaskDto;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.domain.Task;

public class PostgresTaskAdapter implements TaskPort {

    private TaskRepository taskRepository;

    public PostgresTaskAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(Task task) {
        DatabaseTaskDto databaseTaskDto = new DatabaseTaskDto(null, task.getTitle(), task.getDescription(), 
            task.getStatus(),task.getStatus(), task.getAuthorId());
        taskRepository.create(databaseTaskDto);
    }

    @Override
    public Optional<DatabaseTaskDto> getTaskById(Integer taskId) {
        return taskRepository.getTaskById(taskId);
    }

    @Override
    public List<DatabaseTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize) {
        return taskRepository.getTasksByTitle(title, pageNumber, pageSize);
    }

    @Override
    public List<DatabaseTaskDto> getCustomTasksByUserId(Integer userId) {
        return taskRepository.getAllCustomTasksByAuthorId(userId);
    }

    @Override
    public List<DatabaseTaskDto> getExecutedTasksByUserId(Integer userId) {
        return taskRepository.getAllExecuteTaskByUserId(userId);
    }

    @Override
    public Integer getAuthorId(Integer taskId) {
        return taskRepository.getAuthorId(taskId);
    }

    @Override
    public void update(Task task) {        
        DatabaseTaskDto databaseTaskDto = new DatabaseTaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(), task.getAuthorId());
        taskRepository.update(databaseTaskDto);
    }

    @Override
    public boolean remove(Integer taskId) {
        return taskRepository.remove(taskId);
    }

    @Override
    public boolean addExecutor(Integer executorId, Integer taskId) {
        return taskRepository.addExecutor(executorId, taskId);
    }

    @Override
    public boolean removeExecutor(Integer executorId, Integer taskId) {
        return taskRepository.removeExecutor(executorId, taskId);
    }

}
