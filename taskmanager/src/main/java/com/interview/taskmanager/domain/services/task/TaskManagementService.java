package com.interview.taskmanager.domain.services.task;

import java.security.Principal;
import java.util.List;

import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.TaskDetails;
import com.interview.taskmanager.common.dto.TaskFullInfoDto;

public interface TaskManagementService {

    void createTask(TaskDetails taskDetails, Principal currentUser);

    void updateTaskById(Integer taskId, TaskDetails taskDetails, Principal currentUser);

    void deleteTaskById(Integer taskId, Principal currentUser);

    void addExecutor(Integer taskId, Integer userId, Principal currentUser);

    void deleteExecutor(Integer taskId, Integer userId, Principal currentUser);

    TaskFullInfoDto findByIdWithAllInfo(Integer id);

    public List<TaskDetails> findAllTasksByTitleBriefInfo(String title);

    public List<TaskDetails> getAssignedTasksList(Principal currentUser);

    void createComment(Integer taskId, CommentDetails commentDetails, Principal currentUser);

    void updateComment(Integer taskId, Integer commentId, CommentDetails commentDetails, Principal currentUser);

    void deleteComment(Integer commentId, Principal currentUser);

}
