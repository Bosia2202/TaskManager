package com.interview.taskmanager.domain.task;

import java.util.List;

public interface TaskGateway {

    void create(TaskDto taskDto, Integer authorId);

    void updateTitle(Integer taskId, String newTitle);

    void updateDescription(Integer taskId, String description);

    void updateStatus(Integer taskId, TaskStatus status);

    void updatePriority(Integer taskId, TaskPriority priority);

    void remove(Integer taskId);

    void addExecutor(Integer userId, Integer taskId);

    void removeExecutor(Integer userId, Integer taskId);

    TaskDto getTaskById(Integer taskId);

    List<BriefInformationTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize);
}
