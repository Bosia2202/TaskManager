package com.interview.taskmanager.domain.services.task;

import java.security.Principal;
import java.util.List;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.TaskDetails;

public interface TaskManagementService {
    Task findByIdBriefInfo(int id);

    Task findByIdWithAllInfo(int id);

    public List<Task> findAllTasksByTitleBriefInfo(String title);

    public List<Task> findAllTasksByTitleWithAllInfo(String title);

    public List<Task> getListAssignedTasks(Principal currentUser);

    void deleteTaskById(int taskId, Principal currentUser);

    void updateTaskById(int taskId, TaskDetails taskDetails, Principal currentUser);

    void createTask(TaskDetails taskDetails, Principal currentUser);

    void createComment(int taskId, CommentDetails commentDetails, Principal currentUser);

    void updateComment(int taskId,int commentId, CommentDetails commentDetails, Principal currentUser);

    void deleteComment(int commentId, Principal currentUser);

    void addExecutor(int taskId, int userId, Principal currentUser);

    void deleteExecutor(int taskId, int userId, Principal currentUser);
}
