package com.interview.taskmanager.domain.services.task;

import java.util.List;

import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.comment.CommentDto;
import com.interview.taskmanager.common.dto.profile.OwnerTaskDto;
import com.interview.taskmanager.common.dto.profile.UserProfile;
import com.interview.taskmanager.common.dto.task.TaskBriefInfoDto;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.common.dto.task.TaskDto;

import jakarta.persistence.NoResultException;

public interface TaskManagementService {

    void createTask(TaskDetails taskDetails, String username);

    void updateTaskById(Integer taskId, TaskDetails taskDetails, String username);

    void removeTaskById(Integer taskId, String username);

    void addExecutor(Integer taskId, Integer userId, String username);

    void removeExecutor(Integer taskId, Integer userId, String username);

    TaskDto findById(Integer id);

    List<TaskBriefInfoDto> findAllTasksByTitle(String title,Integer pageNumber) throws NoResultException;

    List<OwnerTaskDto> getAssignedTasksList(String username);

    void createComment(CommentDetails commentDetails, String username);

    void updateComment(Integer commentId, CommentDetails commentDetails, String username);

    void removeComment(Integer commentId, String username);

    List<CommentDto> getCommentsByTaskId(Integer taskId, Integer pageNumber);

    UserProfile getUserProfileById(Integer id);

    UserProfile getUserProfileByUsername(String username);

}
