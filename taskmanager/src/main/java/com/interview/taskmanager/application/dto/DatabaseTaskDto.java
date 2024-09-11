package com.interview.taskmanager.application.dto;

public record DatabaseTaskDto(Integer id, String title, String description, String status,
                String priority, Integer authorId) {
}
