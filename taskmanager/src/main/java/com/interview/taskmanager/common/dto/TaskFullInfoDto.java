package com.interview.taskmanager.common.dto;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;

import lombok.Data;

@Data
public class TaskFullInfoDto {
    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String author;
    private List<String> executors;
}
