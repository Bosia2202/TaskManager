package com.interview.taskmanager.application.usecase.comment;

public record CommentDto(Integer id, String content, Integer authorId, String username, Integer taskId) {

}
