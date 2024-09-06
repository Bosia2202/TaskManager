package com.interview.taskmanager.adapters.database;

import java.util.List;

import com.interview.taskmanager.domain.task.BriefInformationTaskDto;
import com.interview.taskmanager.domain.task.TaskDto;
import com.interview.taskmanager.domain.task.TaskGateway;
import com.interview.taskmanager.domain.task.TaskPriority;
import com.interview.taskmanager.domain.task.TaskStatus;

public class TaskGatewayAdapter implements TaskGateway {

    private TaskRepository taskRepository;

    public TaskGatewayAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public boolean create(TaskDto taskDto, Integer authorId) {
        
    }

    @Override
    public void updateTitle(Integer taskId, String newTitle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTitle'");
    }

    @Override
    public void updateDescription(Integer taskId, String description) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDescription'");
    }

    @Override
    public void updateStatus(Integer taskId, TaskStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
    }

    @Override
    public void updatePriority(Integer taskId, TaskPriority priority) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePriority'");
    }

    @Override
    public void remove(Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void addExecutor(Integer userId, Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addExecutor'");
    }

    @Override
    public boolean removeExecutor(Integer userId, Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeExecutor'");
    }

    @Override
    public TaskDto getTaskById(Integer taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTaskById'");
    }

    @Override
    public List<BriefInformationTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTasksByTitle'");
    }

}
