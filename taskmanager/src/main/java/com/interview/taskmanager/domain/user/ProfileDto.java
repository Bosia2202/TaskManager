package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.task.BriefInformationTaskDto;

import java.util.List;

public record ProfileDto(Integer id, String avatarUrl, String username, String post, List<BriefInformationTaskDto> customTasks,
                         List<BriefInformationTaskDto> executingTasks) {
}

