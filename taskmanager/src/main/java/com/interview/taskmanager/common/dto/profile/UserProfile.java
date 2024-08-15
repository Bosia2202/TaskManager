package com.interview.taskmanager.common.dto.profile;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;

import lombok.Data;

@Data
public class UserProfile {
    private String username;
    private String email;
    private List<OwnerTaskDto> ownerTasks;
    private List<ExecutedTaskDto> executedTasks;

    public UserProfile(User user, List<Task> ownerTasks, List<Task> executedTasks) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.ownerTasks = ownerTasks.stream().map(OwnerTaskDtoMapper::toDto).toList();
        this.executedTasks = executedTasks.stream().map(ExecutedTaskDtoMapper::toDto).toList();
    }

}
