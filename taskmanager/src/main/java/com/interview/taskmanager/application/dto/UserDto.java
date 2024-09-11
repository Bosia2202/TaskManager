package com.interview.taskmanager.application.dto;

import java.util.List;

public record UserDto(Integer id, String avatarUrl, String username, List<TaskPreviewDto> customTasks,
                         List<TaskPreviewDto> executingTasks) {
}

