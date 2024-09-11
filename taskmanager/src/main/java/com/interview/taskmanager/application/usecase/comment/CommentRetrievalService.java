package com.interview.taskmanager.application.usecase.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.interview.taskmanager.application.ports.out.CommentPort;
import com.interview.taskmanager.domain.Comment;

@Service
public class CommentRetrievalService {

    private final CommentPort commentPort;

    public CommentRetrievalService(CommentPort commentPort) {
        this.commentPort = commentPort;
    }

    public List<Comment> getComments(Integer taskId, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return commentPort.getComments(taskId, pageNumber, PAGE_SIZE);
    }

    public List<Comment> getSubComments(Integer commentId, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return commentPort.getSubComments(commentId, pageNumber, PAGE_SIZE);
    }

}
