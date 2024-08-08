package com.interview.taskmanager.domain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.taskmanager.adapters.database.repositories.TaskRepository;
import com.interview.taskmanager.adapters.database.repositories.UserRepository;

@Service
public class TaskService implements ExecutorManager {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addExecutor(int taskId, int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addExecutor'");
    }

    @Override
    public void deleteExecutor(int taskId, int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteExecutor'");
    }

}
