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

    public Task(Integer id, String title, String description, String status, String priority,
            Integer authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.valueOf(status);
        this.priority = TaskPriority.valueOf(priority);
        this.authorId = authorId;
    }

    public Task(String title, String description, String status, String priority, Integer authorId) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.valueOf(status);
        this.priority = TaskPriority.valueOf(priority);
        this.authorId = authorId;
    }

    public Integer getStatus() {
        return status.ordinal();
    }

    public Integer getPriority() {
        return priority.ordinal();
    }

}
