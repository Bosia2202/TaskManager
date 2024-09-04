package com.interview.taskmanager.domain.comment;

import com.interview.taskmanager.domain.exception.CommentAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.IdentificationUserService;
import com.interview.taskmanager.domain.security.AccessRightChecker;

public class UpdateCommentService {

    private final CommentGateway commentGateway;

    private final IdentificationUserService identificationUserService;

    private final AccessRightChecker accessRightChecker;

    public UpdateCommentService(CommentGateway commentGateway, IdentificationUserService identificationUserService, AccessRightChecker accessRightChecker) {
        this.commentGateway = commentGateway;
        this.identificationUserService = identificationUserService;
        this.accessRightChecker = accessRightChecker;
    }

    public void updateContent(String newContent, Integer commentId) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(commentId, currentUserId);
        commentGateway.updateComment(newContent, commentId);
    }

    private void checkAccessRight(Integer commentId, Integer currentUserId) {
        if (!accessRightChecker.isUserComment(commentId, currentUserId)) {
            String message = String.format("Comment has not been update. Access denied. Comment [id = '%d']", commentId);
            throw new CommentAccessDeniedRuntimeException(message);
        }
    }

}
