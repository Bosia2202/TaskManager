package com.interview.taskmanager.adapters.database;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.application.dto.NewTaskDto;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.application.usecase.task.TaskPreviewDto;
import com.interview.taskmanager.domain.TaskPriority;
import com.interview.taskmanager.domain.TaskStatus;
import com.interview.taskmanager.application.usecase.task.TaskPresentationDto;

public class TaskGatewayAdapter implements TaskPort {

    private TaskRepository taskRepository;

    public TaskGatewayAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(NewTaskDto taskDto, Integer authorId) {
        taskRepository.create(taskDto, authorId);
    }

    @Override
    public void updateTitle(Integer taskId, String newTitle) {
        taskRepository.updateTitle(newTitle, taskId);
    }

    @Override
    public void updateDescription(Integer taskId, String description) {
        taskRepository.updateDescription(description, taskId);
    }

    @Override
    public void updateStatus(Integer taskId, TaskStatus status) {
        taskRepository.updateStatus(status, taskId);
    }

    @Override
    public void updatePriority(Integer taskId, TaskPriority priority) {
        taskRepository.updatePriority(priority, taskId);
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

    @Override
    public Optional<TaskPresentationDto> getTaskById(Integer taskId) {
        return taskRepository.getTaskById(taskId);
    }

    @Override
    public List<TaskPreviewDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize) {
        return taskRepository.getTasksByTitle(title, pageNumber, pageSize);
    }

}
