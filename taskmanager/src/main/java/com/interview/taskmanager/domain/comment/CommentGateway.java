package com.interview.taskmanager.domain.comment;

import java.util.List;

public interface CommentGateway {

    void createComment(CommentDto commentDto, Integer taskId);

    void updateComment(String newContent, Integer commentId);

    void removeComment(Integer commentId);

    void addSubCommentToComment(CommentDto commentDto, Integer commentId);

    List<CommentDto> getComments(Integer taskId, Integer pageNumber, Integer pageSize);

    List<CommentDto> getSubComments(Integer commentId, Integer pageNumber, Integer pageSize);
}
