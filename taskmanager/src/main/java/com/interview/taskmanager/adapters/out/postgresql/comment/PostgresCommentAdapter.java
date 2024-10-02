package com.interview.taskmanager.adapters.out.postgresql.comment;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.adapters.out.postgresql.comment.repository.CommentRepository;
import com.interview.taskmanager.application.dto.DatabaseCommentDto;
import com.interview.taskmanager.application.dto.DatabaseCreateSubCommentDto;
import com.interview.taskmanager.application.dto.DatabaseSubCommentDto;
import com.interview.taskmanager.application.ports.out.CommentPort;
import com.interview.taskmanager.domain.Comment;

import jakarta.persistence.NoResultException;

public class PostgresCommentAdapter implements CommentPort {

    private final CommentRepository commentRepository;

    public PostgresCommentAdapter(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void save(Comment comment) {
        DatabaseCommentDto databaseComment = new DatabaseCommentDto(comment.getId(), comment.getContent(),
                comment.getAuthorId(), comment.getTaskId());
        commentRepository.save(databaseComment);
    }

    @Override
    public void saveSubComment(Comment comment, Integer commentId) {
        DatabaseCreateSubCommentDto subCommentDto = new DatabaseCreateSubCommentDto(comment.getContent(), comment.getAuthorId(), commentId);
        commentRepository.saveSubComment(subCommentDto);
    }

    @Override
    public List<DatabaseCommentDto> getComments(Integer taskId, Integer pageNumber, Integer pageSize) {
        return commentRepository.getCommentsByTaskId(taskId, pageNumber, pageSize);
    }

    @Override
    public List<DatabaseSubCommentDto> getSubComments(Integer commentId, Integer pageNumber, Integer pageSize) {
        return commentRepository.getSubCommentsByCommentId(commentId, pageNumber, pageSize);
    }

    @Override
    public Optional<DatabaseCommentDto> getCommentById(Integer commentId) {
        try {
            DatabaseCommentDto databaseComment = commentRepository.getCommentById(commentId);
            return Optional.of(databaseComment);
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getAuthorId(Integer commentId) {
        try {
            Integer authorId = commentRepository.getAuthorIdByCommentId(commentId);
            return Optional.of(authorId);
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Comment comment) {
        DatabaseCommentDto databaseComment = new DatabaseCommentDto(comment.getId(), comment.getContent(),
                comment.getAuthorId(), comment.getTaskId());
        commentRepository.updateComment(databaseComment);
    }

    @Override
    public void remove(Integer commentId) {
        commentRepository.removeCommentById(commentId);
    }

    @Override
    public void removeSubComment(Integer commentId) {
        commentRepository.removeSubCommentById(commentId);
    }

}
