package com.interview.taskmanager.application.ports.out;

import java.util.List;

import com.interview.taskmanager.application.dto.NewCommentDto;
import com.interview.taskmanager.domain.Comment;

public interface CommentPort {

    void save(NewCommentDto newCommentDto);

    void saveSubComment(NewCommentDto newCommentDto, Integer commentId);

    List<Comment> getComments(Integer taskId, Integer pageNumber, Integer pAGE_SIZE);

    List<Comment> getSubComments(Integer commentId, Integer pageNumber, Integer pAGE_SIZE);

    void remove(Integer commentId);

    Integer getAuthorId(Integer commentId);

    void updateComment(String newContent, Integer commentId);

}
