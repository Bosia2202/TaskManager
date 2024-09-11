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

    public Task(Integer id, String title, String description, Integer status, Integer priority,
            Integer authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = getTaskStatusById(status);
        this.priority = getTaskPriorityById(priority);
        this.authorId = authorId;
    }

    private TaskStatus getTaskStatusById(Integer id) {
        TaskStatus[] statuses = TaskStatus.values();
        return statuses[id];
    }

    private TaskPriority getTaskPriorityById(Integer id) {
        TaskPriority[] priorities = TaskPriority.values();
        return priorities[id];
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

    public void setStatus(String newStatus) {
        this.status = TaskStatus.valueOf(newStatus);
    }

    public void setPriority(String newPriority) {
        this.priority = TaskPriority.valueOf(newPriority);
    }

}
