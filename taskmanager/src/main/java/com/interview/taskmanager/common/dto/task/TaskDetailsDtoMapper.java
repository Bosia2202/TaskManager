package com.interview.taskmanager.common.dto.task;

import com.interview.taskmanager.adapters.database.models.Task;

public class TaskDetailsDtoMapper {

    private TaskDetailsDtoMapper() {
        throw new IllegalArgumentException();
    }

    public static TaskDetails toDto(Task task) {
        TaskDetails taskDetailsDto = new TaskDetails();
        taskDetailsDto.setTitle(task.getTitle());
        taskDetailsDto.setStatus(task.getStatus());
        taskDetailsDto.setPriority(task.getPriority());
        return taskDetailsDto;
    }
}
