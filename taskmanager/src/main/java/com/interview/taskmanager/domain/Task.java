package com.interview.taskmanager.domain;

import lombok.Getter;

@Getter
public class Task {

    private Integer id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer authorId;

    public Task(Integer id, String title, String description, TaskStatus status, TaskPriority priority,
            Integer authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.authorId = authorId;
    }

    public Task(String title, String description, TaskStatus status, TaskPriority priority, Integer authorId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.authorId = authorId;
    }
}
