package com.interview.taskmanager.application.dto;

public record TaskDto(Integer id, String title, String description, String status, String priority, Integer authorId,
        String authorUsername) {
}
