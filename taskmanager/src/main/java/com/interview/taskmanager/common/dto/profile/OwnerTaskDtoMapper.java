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
        return dto;
    }
}
