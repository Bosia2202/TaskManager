package com.interview.taskmanager.adapters.out.postgresql;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.domain.TaskPriority;
import com.interview.taskmanager.domain.TaskStatus;
import com.interview.taskmanager.application.dto.NewTaskDto;
import com.interview.taskmanager.application.dto.TaskPreviewDto;
import com.interview.taskmanager.application.usecase.task.TaskPresentationDto;

public interface TaskRepository {

    boolean create(NewTaskDto taskDto, Integer authorId);

    Optional<TaskPresentationDto> getTaskById(Integer taskId);

    List<TaskPreviewDto> getTasksByTitle(String title, Integer pageNumber, Integer pAGE_SIZE);

    List<TaskPreviewDto> getCustomTaskByUserId(Integer userId);

    List<TaskPreviewDto> getExecutingTasksByUserId(Integer userId);

    boolean updateTitle(String newTitle, Integer taskId);

    boolean updateDescription(String description, Integer taskId);

    boolean updateStatus(TaskStatus status, Integer taskId);

    boolean updatePriority(TaskPriority priority, Integer taskId);

    boolean remove(Integer taskId);

    boolean addExecutor(Integer executorId, Integer taskId);

    boolean removeExecutor(Integer executorId, Integer taskId);

    

}
