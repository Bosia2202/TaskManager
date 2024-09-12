package com.interview.taskmanager.application.usecase.comment;

public record SubCommentDto(Integer id, String content, Integer authorId, Integer commentId) {
}

