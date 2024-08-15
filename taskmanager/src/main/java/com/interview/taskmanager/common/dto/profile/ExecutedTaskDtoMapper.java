package com.interview.taskmanager.common.dto.profile;

import com.interview.taskmanager.adapters.database.models.Task;

public class ExecutedTaskDtoMapper {
    private ExecutedTaskDtoMapper() {
        throw new IllegalArgumentException();
    }

    public static ExecutedTaskDto toDto(Task task) {
        ExecutedTaskDto executedTaskDto = new ExecutedTaskDto();
        executedTaskDto.setId(task.getId());
        executedTaskDto.setTitle(task.getTitle());
        executedTaskDto.setPriority(task.getPriority());
        executedTaskDto.setStatus(task.getStatus());
        return executedTaskDto;
    }

}
