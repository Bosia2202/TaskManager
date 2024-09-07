package com.interview.taskmanager.adapters.database;

import com.interview.taskmanager.domain.task.BriefTaskDto;
import com.interview.taskmanager.domain.task.TaskDto;
import com.interview.taskmanager.domain.task.TaskPresentationDto;
import com.interview.taskmanager.domain.task.TaskPriority;
import com.interview.taskmanager.domain.task.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    boolean create(TaskDto taskDto, Integer authorId);

    Optional<TaskPresentationDto> getTaskById(Integer taskId);

    List<BriefTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pAGE_SIZE);

    List<BriefTaskDto> getCustomTaskByUserId(Integer userId);

    List<BriefTaskDto> getExecutingTasksByUserId(Integer userId);

    boolean updateTitle(String newTitle, Integer taskId);

    boolean updateDescription(String description, Integer taskId);

    boolean updateStatus(TaskStatus status, Integer taskId);

    boolean updatePriority(TaskPriority priority, Integer taskId);

    boolean remove(Integer taskId);

    boolean addExecutor(Integer executorId, Integer taskId);

    boolean removeExecutor(Integer executorId, Integer taskId);

    

}
