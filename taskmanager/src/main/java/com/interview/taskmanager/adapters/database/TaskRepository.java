package com.interview.taskmanager.adapters.database;

import com.interview.taskmanager.domain.task.BriefInformationTaskDto;

import java.util.List;

public interface TaskRepository {

    List<BriefInformationTaskDto> getCustomTaskByUserId(Integer userId);

    List<BriefInformationTaskDto> getExecutingTasksByUserId(Integer userId);

}
