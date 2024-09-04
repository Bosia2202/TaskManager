package com.interview.taskmanager.domain.taskmanager.comment;

import com.interview.taskmanager.domain.exception.CommentAccessDeniedRuntimeException;

import java.util.List;

public class CommentService {

    private final CommentGateway commentGateway;
    private final CommentRightChecker commentRightChecker;

    public CommentService(CommentGateway commentGateway, CommentRightChecker commentRightChecker) {
        this.commentGateway = commentGateway;
        this.commentRightChecker = commentRightChecker;
    }

    public void createComment(CommentDto commentDto, Integer taskId) {
        commentGateway.createComment(commentDto, taskId);
    }

    public void updateCommentContent(String newContent, Integer commentId, Integer currentUserId) {
        checkAccessRightChecker(commentId, currentUserId);
        commentGateway.updateComment(newContent, commentId);
    }

    private void checkAccessRightChecker(Integer commentId, Integer currentUserId) {
        if (!commentRightChecker.isUserComment(commentId, currentUserId)) {
            String message = String.format("Comment has not been modification. Access denied. Comment [id = '%d']", commentId);
            throw new CommentAccessDeniedRuntimeException(message);
        }
    }

    public void removeComment(Integer commentId, Integer currentUserId) {
        checkAccessRightChecker(commentId, currentUserId);
        commentGateway.removeComment(commentId);
    }

    public List<CommentDto> getComments(Integer taskId, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return commentGateway.getComments(taskId, pageNumber, PAGE_SIZE);
    }

    public void addSubCommentToComment(CommentDto commentDto, Integer commentId) {
        commentGateway.addSubCommentToComment(commentDto, commentId);
    }

}
