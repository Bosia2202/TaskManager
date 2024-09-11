package com.interview.taskmanager.application.dto;

import com.interview.taskmanager.domain.TaskPriority;
import com.interview.taskmanager.domain.TaskStatus;

public record NewTaskDto(String title, String description, TaskStatus status,
                      TaskPriority priority) {

}