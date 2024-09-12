package com.interview.taskmanager.application.usecase.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.interview.taskmanager.application.dto.DatabaseCommentDto;
import com.interview.taskmanager.application.dto.DatabaseSubCommentDto;
import com.interview.taskmanager.application.ports.out.CommentPort;
import com.interview.taskmanager.application.ports.out.UserPort;

@Service
public class CommentRetrievalService {

    private final CommentPort commentPort;

    private final UserPort userPort;

    public CommentRetrievalService(CommentPort commentPort, UserPort userPort) {
        this.commentPort = commentPort;
        this.userPort = userPort;
    }

    public List<CommentDto> getComments(Integer taskId, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        List<DatabaseCommentDto> comments = commentPort.getComments(taskId, pageNumber, PAGE_SIZE);
        return comments.stream().map(subComment -> new CommentDto(subComment.id(), subComment.content(),
                subComment.authorId(), userPort.getUsernameById(subComment.authorId()), subComment.taskId())).toList();
    }

    public List<SubCommentDto> getSubComments(Integer commentId, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        List<DatabaseSubCommentDto> subComments = commentPort.getSubComments(commentId, pageNumber, PAGE_SIZE);
        return subComments.stream().map(subComment -> new SubCommentDto(subComment.id(), subComment.content(),
                subComment.authorId(), subComment.commentId())).toList();
    }

}
