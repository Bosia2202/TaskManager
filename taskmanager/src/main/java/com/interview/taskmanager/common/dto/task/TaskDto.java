package com.interview.taskmanager.common.dto.task;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;
import com.interview.taskmanager.common.dto.profile.AuthorDto;
import com.interview.taskmanager.common.dto.profile.ExecutorDto;

import lombok.Data;

@Data
public class TaskDto {
    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private AuthorDto author;
    private List<ExecutorDto> executors;
}
