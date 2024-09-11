package com.interview.taskmanager.application.usecase.task;

public record TaskPreviewDto(String title, String status,
        String priority, Integer authorId, String authorUsername) {
}
