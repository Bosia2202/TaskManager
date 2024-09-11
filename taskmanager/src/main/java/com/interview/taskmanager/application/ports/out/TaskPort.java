package com.interview.taskmanager.application.ports.out;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.application.dto.DatabaseTaskDto;
import com.interview.taskmanager.application.dto.NewTaskDto;
import com.interview.taskmanager.domain.Task;

public interface TaskPort {

    void create(NewTaskDto taskDto, Integer authorId);

    void updateTitle(Integer taskId, String newTitle);

    void updateDescription(Integer taskId, String description);

    void updateStatus(Integer taskId, String status);

    void updatePriority(Integer taskId, String priority);

    boolean remove(Integer taskId);

    boolean addExecutor(Integer userId, Integer taskId);

    boolean removeExecutor(Integer userId, Integer taskId);

    Optional<DatabaseTaskDto> getTaskById(Integer taskId);

    List<DatabaseTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize);

    List<DatabaseTaskDto> getCustomTasksByUserId(Integer userId);

    List<DatabaseTaskDto> getExecutedTasksByUserId(Integer userId);

    Integer getAuthorId(Integer taskId);

    void create(Task task);

    void update(Task task);
}
