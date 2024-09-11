package com.interview.taskmanager.application.dto;

public record DatabaseTaskDto(Integer id, String title, String description, Integer status,
                Integer priority, Integer authorId) {
}
