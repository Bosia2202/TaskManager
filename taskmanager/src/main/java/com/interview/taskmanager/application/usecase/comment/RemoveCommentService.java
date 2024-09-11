package com.interview.taskmanager.application.usecase.comment;

import org.springframework.stereotype.Service;

import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.CommentPort;
import com.interview.taskmanager.infra.exception.CommentAccessDeniedRuntimeException;

@Service
public class RemoveCommentService {

    private final CommentPort commentPort;

    private final SecurityPort securityPort;

    public RemoveCommentService(CommentPort commentPort, SecurityPort securityPort) {
        this.commentPort = commentPort;
        this.securityPort = securityPort;
    }

    public void remove(Integer commentId) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(commentId, currentUserId);
        commentPort.remove(commentId);
    }

    private void checkAccessRight(Integer commentId, Integer currentUserId) {
        Integer authorId = commentPort.getAuthorId(commentId);
        if (!authorId.equals(currentUserId)) {
            String message = String.format("Comment has not been remove. Access denied. Comment [id = '%d']",
                    commentId);
            throw new CommentAccessDeniedRuntimeException(message);
        }
    }

}
