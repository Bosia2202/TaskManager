package com.interview.taskmanager.domain.task;

public record BriefTaskDto(String title, TaskStatus status,
        TaskPriority priority, TaskAuthorDto author) {
}
