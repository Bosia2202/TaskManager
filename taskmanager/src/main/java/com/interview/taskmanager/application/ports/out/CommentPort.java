package com.interview.taskmanager.application.ports.out;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.application.dto.DatabaseCommentDto;
import com.interview.taskmanager.application.dto.DatabaseSubCommentDto;
import com.interview.taskmanager.domain.Comment;

public interface CommentPort {

    void save(Comment comment);

    void saveSubComment(Comment comment, Integer commentId);

    List<DatabaseCommentDto> getComments(Integer taskId, Integer pageNumber, Integer pageSize);

    List<DatabaseSubCommentDto> getSubComments(Integer commentId, Integer pageNumber, Integer pageSize);

    Optional<DatabaseCommentDto> getCommentById(Integer commentId);

    Optional<Integer> getAuthorId(Integer commentId);

    void update(Comment comment);

    void remove(Integer commentId);

    void removeSubComment(Integer commentId);
}
