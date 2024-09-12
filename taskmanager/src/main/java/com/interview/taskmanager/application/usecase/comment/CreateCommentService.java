package com.interview.taskmanager.application.usecase.comment;

import org.springframework.stereotype.Service;

import com.interview.taskmanager.domain.Comment;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.CommentPort;

@Service
public class CreateCommentService {

    private final CommentPort commentPort;

    private final SecurityPort securityPort;

    public CreateCommentService(CommentPort commentPort, SecurityPort securityPort) {
        this.commentPort = commentPort;
        this.securityPort = securityPort;
    }

    public void createComment(String content, Integer taskId) {
        Integer currentUserId = securityPort.getCurrentUserId();
        Comment comment = new Comment(content, currentUserId, taskId);
        commentPort.save(comment);
    }

    public void createSubComment(String content, Integer commentId, Integer taskId) {
        Integer currentUserId = securityPort.getCurrentUserId();
        Comment comment = new Comment(content, currentUserId, taskId);
        commentPort.saveSubComment(comment, commentId);
    }
}
