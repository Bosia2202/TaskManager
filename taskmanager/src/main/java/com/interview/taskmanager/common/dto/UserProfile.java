package com.interview.taskmanager.common.dto;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.User;

import lombok.Data;

@Data
public class UserProfile {
    private String username;
    private String email;
    List<TaskOwnerDto> tasks;
    
    public UserProfile(User user) {
        TaskOwnerDtoMapper mapper = new TaskOwnerDtoMapper();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.tasks = (user.getOwnerTasks().stream().map(mapper::toDto).toList());
    }
}
