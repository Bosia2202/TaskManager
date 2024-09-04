package com.interview.taskmanager.domain.comment;

import com.interview.taskmanager.domain.exception.CommentAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.AccessRightChecker;
import com.interview.taskmanager.domain.security.IdentificationUserService;

public class RemoveCommentService {

    private final CommentGateway commentGateway;

    private final IdentificationUserService identificationUserService;

    private final AccessRightChecker accessRightChecker;

    public RemoveCommentService(CommentGateway commentGateway, IdentificationUserService identificationUserService, AccessRightChecker accessRightChecker) {
        this.commentGateway = commentGateway;
        this.identificationUserService = identificationUserService;
        this.accessRightChecker = accessRightChecker;
    }

    public void remove(Integer commentId) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        checkAccessRight(commentId, currentUserId);
        commentGateway.removeComment(commentId);
    }

    private void checkAccessRight(Integer commentId, Integer currentUserId) {
        if (!accessRightChecker.isUserComment(commentId, currentUserId)) {
            String message = String.format("Comment has not been remove. Access denied. Comment [id = '%d']", commentId);
            throw new CommentAccessDeniedRuntimeException(message);
        }
    }

}
