package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.CommentRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.adapters.database.repositories.jpa.CommentJpaRepository;
import com.interview.taskmanager.common.dto.CommentDetails;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CommentRepository implements CommentRepositoryAdapter {

    private CommentJpaRepository commentJpaRepository;

    @Override
    public void createComment(Comment comment) {
        commentJpaRepository.save(comment);
    }

    @Override
    @Transactional
    public void updateComment(Integer id, CommentDetails commentDetails) throws EntityNotFoundException {
        Comment comment = commentJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Comment [id = '%d'] wasn't updated", id)));
        comment.setDetails(commentDetails);
        commentJpaRepository.save(comment);
    }

    @Override
    @Transactional
    public void removeComment(Integer id) {
        commentJpaRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByTaskId(Integer id) {
        //TODO: реализовать метод 
    }

    //TODO: Сделать рефакторинг комментариев 

}
