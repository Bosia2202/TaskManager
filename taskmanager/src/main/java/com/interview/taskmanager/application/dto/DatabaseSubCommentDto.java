package com.interview.taskmanager.application.dto;

public record DatabaseSubCommentDto(Integer id, String content, Integer authorId, Integer commentId) {
}
