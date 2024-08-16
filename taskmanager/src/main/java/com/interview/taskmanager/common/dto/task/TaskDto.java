package com.interview.taskmanager.common.dto.task;

import java.util.List;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.models.statuses.TaskPriority;
import com.interview.taskmanager.adapters.database.models.statuses.TaskStatus;
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

    private TaskDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.status = builder.status;
        this.priority = builder.priority;
        this.author = builder.author;
        this.executors = builder.executors;
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private TaskStatus status;
        private TaskPriority priority;
        private AuthorDto author;
        private List<ExecutorDto> executors;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder priority(TaskPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder author(User user) {
            this.author = new AuthorDto(user.getId(), user.getUsername());
            return this;
        }

        public Builder executors(List<User> executors) {
            this.executors = executors.stream()
                    .map(executor -> new ExecutorDto(executor.getId(), executor.getUsername())).toList();
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this);
        }
    }
}
