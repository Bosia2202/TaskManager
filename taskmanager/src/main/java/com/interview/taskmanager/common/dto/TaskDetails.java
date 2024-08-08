package com.interview.taskmanager.common.dto;

import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskDetails {

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;

}
