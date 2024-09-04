package com.interview.taskmanager.domain.task;

public record TaskDto(String title, String description, TaskStatus status,
                      TaskPriority priority) {

}