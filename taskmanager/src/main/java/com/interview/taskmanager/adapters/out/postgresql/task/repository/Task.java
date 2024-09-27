package com.interview.taskmanager.adapters.out.postgresql.task.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private Integer priority;

    @Column(nullable = false)
    private Integer authorId;

    public Task(String title, String description, Integer status, Integer priority, Integer authorId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.authorId = authorId;
    }

}
