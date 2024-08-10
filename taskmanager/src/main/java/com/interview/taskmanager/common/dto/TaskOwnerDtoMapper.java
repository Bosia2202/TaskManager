package com.interview.taskmanager.common.dto;

import com.interview.taskmanager.adapters.database.models.Task;

public class TaskOwnerDtoMapper {
    public TaskOwnerDto toDto(Task task) {
        TaskOwnerDto dto = new TaskOwnerDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setExecutors(task.getExecutors().stream().map(ex -> ex.getUsername()).toList());
        return dto;
    }
}
