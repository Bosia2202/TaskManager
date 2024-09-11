package com.interview.taskmanager.application.dto;

import java.util.List;

import com.interview.taskmanager.application.usecase.task.TaskPreviewDto;

public record UserDto(Integer id, String avatarUrl, String username, List<TaskPreviewDto> customTasks,
                         List<TaskPreviewDto> executingTasks) {
}

