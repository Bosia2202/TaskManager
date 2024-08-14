package com.interview.taskmanager.common.dto.task;

import com.interview.taskmanager.adapters.database.models.Task;

public class TaskBriefInfoDtoMapper {
    
    private TaskBriefInfoDtoMapper() {
        throw new IllegalArgumentException();
    }

    public static TaskBriefInfoDto toDto(Task task) {
        TaskBriefInfoDto taskBriefInfoDto = new TaskBriefInfoDto();
        taskBriefInfoDto.setId(task.getId());
        taskBriefInfoDto.setTitle(task.getTitle());
        taskBriefInfoDto.setStatus(task.getStatus());
        taskBriefInfoDto.setPriority(task.getPriority());
        return taskBriefInfoDto;
    }
}
