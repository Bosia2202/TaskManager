package com.interview.taskmanager.infra.postgresql;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.TaskRepository;
import com.interview.taskmanager.domain.task.BriefInformationTaskDto;

@Repository
public class PostgresTaskRepository implements TaskRepository {

    @Override
    public List<BriefInformationTaskDto> getCustomTaskByUserId(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomTaskByUserId'");
    }

    @Override
    public List<BriefInformationTaskDto> getExecutingTasksByUserId(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExecutingTasksByUserId'");
    }

}
