package com.interview.taskmanager.domain.security;

public interface AccessRightChecker {

    boolean isUserTask(Integer taskId, Integer userId);

    boolean isUserComment(Integer commentId, Integer currentUserId);
}
