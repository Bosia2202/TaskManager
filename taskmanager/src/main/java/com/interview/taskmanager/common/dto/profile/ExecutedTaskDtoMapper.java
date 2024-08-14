package com.interview.taskmanager.common.dto.profile;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;

public class ExecutedTaskDtoMapper {
    private ExecutedTaskDtoMapper() {
        throw new IllegalArgumentException();
    }

    public static ExecutedTaskDto toDto(Task task) {
        ExecutedTaskDto executedTaskDto = new ExecutedTaskDto();
        executedTaskDto.setId(task.getId());
        executedTaskDto.setTitle(task.getTitle());
        executedTaskDto.setPriority(task.getPriority());
        executedTaskDto.setStatus(task.getStatus());
        User author = task.getAuthor();
        executedTaskDto.setAuthor(new AuthorDto(author.getId(), author.getUsername()));
        return executedTaskDto;
    }

}
