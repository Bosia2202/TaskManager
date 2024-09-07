package com.interview.taskmanager.domain.task;

import java.util.List;
import java.util.Optional;

public interface TaskGateway {

    void create(TaskDto taskDto, Integer authorId);

    void updateTitle(Integer taskId, String newTitle);

    void updateDescription(Integer taskId, String description);

    void updateStatus(Integer taskId, TaskStatus status);

    void updatePriority(Integer taskId, TaskPriority priority);

    boolean remove(Integer taskId);

    boolean addExecutor(Integer userId, Integer taskId);

    boolean removeExecutor(Integer userId, Integer taskId);

    Optional<TaskPresentationDto> getTaskById(Integer taskId);

    List<BriefTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize);
}
