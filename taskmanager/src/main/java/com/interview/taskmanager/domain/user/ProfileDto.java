package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.task.BriefTaskDto;

import java.util.List;

public record ProfileDto(Integer id, String avatarUrl, String username, List<BriefTaskDto> customTasks,
                         List<BriefTaskDto> executingTasks) {
}

