package com.interview.taskmanager.application.usecase.comment;

import org.springframework.stereotype.Service;

import com.interview.taskmanager.application.dto.DatabaseCommentDto;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.CommentPort;
import com.interview.taskmanager.domain.Comment;
import com.interview.taskmanager.infra.exception.CommentAccessDeniedRuntimeException;
import com.interview.taskmanager.infra.exception.CommentNotFoundRuntimeException;

@Service
public class UpdateCommentService {

    private final CommentPort commentPort;

    private final SecurityPort securityPort;

    public UpdateCommentService(CommentPort commentPort, SecurityPort securityPort) {
        this.commentPort = commentPort;
        this.securityPort = securityPort;
    }

    public void updateContent(String newContent, Integer commentId) {
        Integer currentUserId = securityPort.getCurrentUserId();
        checkAccessRight(commentId, currentUserId);
        DatabaseCommentDto databaseComment = commentPort.getCommentById(commentId)
                .orElseThrow(() -> new CommentNotFoundRuntimeException("Comment not found"));
        Comment comment = new Comment(databaseComment.id(), newContent, databaseComment.authorId(),
                databaseComment.taskId());
        commentPort.update(comment);
    }

    private void checkAccessRight(Integer commentId, Integer currentUserId) {
        Integer authorId = commentPort.getAuthorId(commentId)
                .orElseThrow(() -> new CommentNotFoundRuntimeException("Comment wasn't found"));
        if (!currentUserId.equals(authorId)) {
            String message = String.format("Comment has not been update. Access denied. Comment [id = '%d']",
                    commentId);
            throw new CommentAccessDeniedRuntimeException(message);
        }
    }

}
