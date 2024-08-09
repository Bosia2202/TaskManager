package com.interview.taskmanager.adapters.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.adapters.database.repositories.CommentRepository;
import com.interview.taskmanager.common.dto.CommentDetails;

import jakarta.persistence.EntityNotFoundException;

@Repository
public class CommentRepositoryAdapter implements CommentRepository {
    private final JpaRepository<Comment, Integer> repository;

    public CommentRepositoryAdapter(JpaRepository<Comment, Integer> jpaRepository) {
        this.repository = jpaRepository;
    }

    @Override
    @EntityGraph(value = "comment-entity-information")
    @Transactional(readOnly = true)
    public Comment findCommentById(int commentId) {
        return repository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Comment with id = '%d' doesn't found", commentId)));
    }

    @Override
    @Transactional
    public void deleteComment(Comment comment) {
        repository.deleteById(comment.getId());
    }

    @Override
    @Transactional
    public void updateComment(int commentId, CommentDetails commentDetails) {
        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Comment with id = '%d' doesn't found", commentId)));
        comment.setDetails(commentDetails);
        repository.save(comment);
    }

    @Override
    public void createComment(Comment comment) {
        repository.save(comment);
    }

}
