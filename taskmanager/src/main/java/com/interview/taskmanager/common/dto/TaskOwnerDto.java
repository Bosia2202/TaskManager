package com.interview.taskmanager.common.dto;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;

import lombok.Data;

@Data
public class TaskOwnerDto {
    private int id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private List<String> executors;

}
