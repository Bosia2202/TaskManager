package com.interview.taskmanager.application.dto;

public record TaskPreviewDto(String title, String status,
        String priority, Integer authorId, String authorUsername) {
}
