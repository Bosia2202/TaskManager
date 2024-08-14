package com.interview.taskmanager.common.dto.task;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.profile.AuthorDto;
import com.interview.taskmanager.common.dto.profile.ExecutorDto;

public class TaskDtoMapper {

    private TaskDtoMapper() {
        throw new IllegalArgumentException();
    }

    public static TaskDto toDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());
        User author = task.getAuthor();
        taskDto.setAuthor(new AuthorDto(author.getId(), author.getUsername()));
        taskDto.setExecutors(task.getExecutors().stream()
                .map(executor -> new ExecutorDto(executor.getId(), executor.getUsername())).toList());
        return taskDto;
    }
}
