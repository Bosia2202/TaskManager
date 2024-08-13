package com.interview.taskmanager.common.dto;

import com.interview.taskmanager.adapters.database.models.Task;

public class TaskFullInfoDtoMapper {
    public TaskFullInfoDto toDto(Task task) {
        TaskFullInfoDto taskFullInfoDto = new TaskFullInfoDto();
        taskFullInfoDto.setId(task.getId());
        taskFullInfoDto.setTitle(task.getTitle());
        taskFullInfoDto.setDescription(task.getDescription());
        taskFullInfoDto.setStatus(task.getStatus());
        taskFullInfoDto.setPriority(task.getPriority());
        taskFullInfoDto.setAuthor(task.getAuthor().getUsername());
        taskFullInfoDto.setExecutors(task.getExecutors().stream().map(executor -> executor.getUsername()).toList());
        return taskFullInfoDto;
    }
}
