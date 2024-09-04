package com.interview.taskmanager.domain.comment;

import com.interview.taskmanager.domain.security.IdentificationUserService;

public class CreateCommentService {

    private final CommentGateway commentGateway;

    private final IdentificationUserService identificationUserService;

    public CreateCommentService(CommentGateway commentGateway, IdentificationUserService userIdentificationService) {
        this.commentGateway = commentGateway;
        this.identificationUserService = userIdentificationService;
    }

    public void createComment(String content, Integer taskId) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        CommentDto commentDto = new CommentDto(content, currentUserId);
        commentGateway.createComment(commentDto, taskId);
    }

    public void createSubComment(String content, Integer commentId) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        CommentDto commentDto = new CommentDto(content, currentUserId);
        commentGateway.addSubCommentToComment(commentDto, commentId);
    }
}
