package com.interview.taskmanager.domain.taskmanager.comment;

public interface CommentRightChecker {

    boolean isUserComment(Integer commentId, Integer currentUserId);

}
