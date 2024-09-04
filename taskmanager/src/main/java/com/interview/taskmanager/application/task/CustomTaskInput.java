package com.interview.taskmanager.application.task;

import com.interview.taskmanager.domain.task.TaskDto;

public interface CustomTaskInput {

    void create(TaskDto taskDto, Integer authorId);

    void updateTitle(Integer taskId, String newTitle, Boolean accessRight);

    void updateDescription(Integer taskId, String description, Boolean accessRight);

    void updateStatus(Integer taskId, Integer status, Boolean accessRight);

    void updatePriority(Integer taskId, Integer priority, Boolean accessRight);

    void remove(Integer taskId, Boolean accessRight);

}
