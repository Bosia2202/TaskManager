package com.interview.taskmanager.common.dto.profile;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;

import lombok.Data;

@Data
public class OwnerTaskDto {
    private Integer id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private List<ExecutorDto> executors;
}
