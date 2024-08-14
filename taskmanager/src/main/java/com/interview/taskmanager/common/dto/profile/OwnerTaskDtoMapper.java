package com.interview.taskmanager.common.dto.profile;

import com.interview.taskmanager.adapters.database.models.Task;

public class OwnerTaskDtoMapper {
    private OwnerTaskDtoMapper() {
        throw new IllegalArgumentException();
    }

    public static OwnerTaskDto toDto(Task task) {
        OwnerTaskDto dto = new OwnerTaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setExecutors(task.getExecutors().stream().map(streamExecutor -> 
            new ExecutorDto(streamExecutor.getId(),streamExecutor.getUsername())).toList());
        return dto;
    }
}
