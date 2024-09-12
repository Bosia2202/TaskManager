package com.interview.taskmanager.application.dto;

public record DatabaseCommentDto(Integer id, String content, Integer authorId, Integer taskId) {

}
