package com.interview.taskmanager.common.dto.profile;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.User;

import lombok.Data;

@Data
public class UserProfile {
    private String username;
    private String email;
    private List<OwnerTaskDto> ownerTasks;
    private List<ExecutedTaskDto> executedTasks;

    public UserProfile(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.ownerTasks = (user.getOwnerTasks().stream().map(ownerTask -> OwnerTaskDtoMapper.toDto(ownerTask)).toList());
        this.executedTasks = (user.getExecutedTasks().stream().map(executedTask -> ExecutedTaskDtoMapper.toDto(executedTask))).toList();
    }
}
