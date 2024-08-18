package com.interview.taskmanager.domain.services.task;

import java.security.Principal;
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

    void createTask(TaskDetails taskDetails, Principal currentUser);

    void updateTaskById(Integer taskId, TaskDetails taskDetails, Principal currentUser);

    void removeTaskById(Integer taskId, Principal currentUser);

    void addExecutor(Integer taskId, Integer userId, Principal currentUser);

    void removeExecutor(Integer taskId, Integer userId, Principal currentUser);

    TaskDto findById(Integer id);

    List<TaskBriefInfoDto> findAllTasksByTitle(String title,Integer pageNumber) throws NoResultException;

    List<OwnerTaskDto> getAssignedTasksList(Principal currentUser);

    void createComment(CommentDetails commentDetails, Principal currentUser);

    void updateComment(Integer commentId, CommentDetails commentDetails, Principal currentUser);

    void removeComment(Integer commentId, Principal currentUser);

    List<CommentDto> getCommentsByTaskId(Integer taskId, Integer pageNumber);

    UserProfile getUserProfileById(Integer id);

    UserProfile getUserProfileByUsername(String username);

}
