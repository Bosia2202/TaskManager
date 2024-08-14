package com.interview.taskmanager.common.dto.task;

import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;

import lombok.Data;

@Data
public class TaskBriefInfoDto {
    private Integer id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
}
