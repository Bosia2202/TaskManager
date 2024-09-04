package com.interview.taskmanager.domain.comment;

import java.util.List;

public class CommentRetrievalService {

    private final CommentGateway commentGateway;

    public CommentRetrievalService(CommentGateway commentGateway) {
        this.commentGateway = commentGateway;
    }

    public List<CommentDto> getComments(Integer taskId, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return commentGateway.getComments(taskId, pageNumber, PAGE_SIZE);
    }

    public List<CommentDto> getSubComments(Integer commentId, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return commentGateway.getSubComments(commentId, pageNumber, PAGE_SIZE);
    }

}
