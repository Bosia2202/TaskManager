package com.interview.taskmanager.adapters.database;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.domain.task.BriefTaskDto;
import com.interview.taskmanager.domain.task.TaskDto;
import com.interview.taskmanager.domain.task.TaskGateway;
import com.interview.taskmanager.domain.task.TaskPresentationDto;
import com.interview.taskmanager.domain.task.TaskPriority;
import com.interview.taskmanager.domain.task.TaskStatus;

public class TaskGatewayAdapter implements TaskGateway {

    private TaskRepository taskRepository;

    public TaskGatewayAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(TaskDto taskDto, Integer authorId) {
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
    public List<BriefTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize) {
        return taskRepository.getTasksByTitle(title, pageNumber, pageSize);
    }

}
