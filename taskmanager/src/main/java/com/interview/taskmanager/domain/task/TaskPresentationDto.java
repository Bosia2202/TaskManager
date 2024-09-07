package com.interview.taskmanager.domain.task;

public record TaskPresentationDto(String title, String description, TaskStatus status,
        TaskPriority priority, TaskAuthorDto author) {

}
