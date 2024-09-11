package com.interview.taskmanager.application.dto;

public record NewCommentDto(

        String content,

        Integer authorId,

        String username,

        Integer taskId) {}
