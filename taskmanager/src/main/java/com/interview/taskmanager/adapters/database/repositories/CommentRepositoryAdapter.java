package com.interview.taskmanager.adapters.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.common.dto.CommentDetails;

import jakarta.persistence.EntityNotFoundException;

@Repository
public class CommentRepositoryAdapter implements CommentRepository {
    private final JpaRepository<Comment, Integer> repository;

    public CommentRepositoryAdapter(JpaRepository<Comment, Integer> jpaRepository) {
        this.repository = jpaRepository;
    }

    @Override
    public Comment findCommentById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id" + id + "doesn't found"));
    }

    @Override
    public void deleteCommentById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void updateComment(int id, CommentDetails commentDetails) {
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id" + id + "doesn't found"));
        comment.setDetails(commentDetails);
        repository.save(comment);
    }

    @Override
    public void createComment(CommentDetails commentDetails) {
        Comment comment = new Comment();
        comment.setDetails(commentDetails);
        repository.save(comment);
    }

}
