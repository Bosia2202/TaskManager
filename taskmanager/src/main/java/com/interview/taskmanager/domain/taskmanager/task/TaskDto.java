package com.interview.taskmanager.domain.taskmanager.task;

public record TaskDto(String title, String description, TaskStatus status,
                      TaskPriority priority) {

}