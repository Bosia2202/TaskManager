package com.interview.taskmanager.application.usecase.search;

public record TaskDto(Integer id, String title, String description, String status, String priority, Integer authorId,
        String authorUsername) {
}
