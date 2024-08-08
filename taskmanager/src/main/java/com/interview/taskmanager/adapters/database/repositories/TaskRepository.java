package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.common.dto.TaskDetails;

public interface TaskRepository {
    @EntityGraph(value = "task-entity-graph-with-brief-information")
    Task findByIdBriefInfo(int id);

    @EntityGraph(value = "task-entity-graph-with-all-information")
    Task findByIdWithAllInfo(int id);

    @EntityGraph(value = "task-entity-graph-with-brief-information")
    public List<Task> findAllTasksByTitleBriefInfo(String title);

    @EntityGraph(value = "task-entity-graph-with-all-information")
    public List<Task> findAllTasksByTitleWithAllInfo(String title);

    void deleteTaskById(int id);

    void updateTaskById(int id, TaskDetails taskDetails);

    void createTask(TaskDetails taskDetails);

}
