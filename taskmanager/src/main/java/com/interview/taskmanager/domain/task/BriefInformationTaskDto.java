package com.interview.taskmanager.domain.task;

public record BriefInformationTaskDto(String title, String username, TaskStatus status, TaskPriority priority) {
}

