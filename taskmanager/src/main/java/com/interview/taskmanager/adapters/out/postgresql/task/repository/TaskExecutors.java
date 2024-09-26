package com.interview.taskmanager.adapters.out.postgresql.task.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "task_executors")
class TaskExecutors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "task_id", nullable = false)
    private Integer taskId; 
    
    @Column(name = "executor_id", nullable = false)
    private Integer executorId;
}
