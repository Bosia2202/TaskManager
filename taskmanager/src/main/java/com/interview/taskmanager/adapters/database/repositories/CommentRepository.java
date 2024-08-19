package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.CommentRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.adapters.database.repositories.jpa.CommentCrudJpaOperation;
import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.comment.CommentDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepository implements CommentRepositoryAdapter {

    private final CommentCrudJpaOperation commentCrudJpaOperation;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createComment(Comment comment) {
        commentCrudJpaOperation.save(comment);
    }

    @Override
    @Transactional
    public void updateComment(Integer id, CommentDetails commentDetails) throws NoResultException {
        Comment comment = commentCrudJpaOperation.findById(id)
                .orElseThrow(() -> new NoResultException(
                        String.format("Comment [id = '%d'] wasn't found and hasn't been updated", id)));
        comment.setDetails(commentDetails);
        commentCrudJpaOperation.save(comment);
    }

    @Override
    @Transactional
    public void removeComment(Integer id) throws NoResultException {
        if (commentCrudJpaOperation.existsById(id)) {
            commentCrudJpaOperation.deleteById(id);
        } else {
            throw new NoResultException(String.format("Comment [id = '%d'] wasn't found and hasn't been deleted", id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByTaskId(Integer id, Integer pageNumber) {
        final int PAGE_SIZE = 15;
        TypedQuery<Comment> commentQuery = entityManager.createQuery(
                "SELECT c FROM Comment c JOIN FETCH c.author WHERE c.task.id = :taskId",
                Comment.class);
        commentQuery.setParameter("taskId", id);
        commentQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
        commentQuery.setMaxResults(PAGE_SIZE);
        return commentQuery.getResultStream().map(comment -> new CommentDto.Builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .build()).toList();
    }

    @Override
    public boolean isUsersComment(String username, Integer commentId) {
        if (commentCrudJpaOperation.existsById(commentId)) {
            TypedQuery<Boolean> query = entityManager.createQuery(
                    "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Comment c JOIN c.author a WHERE c.id = :commentId AND a.username = :username",
                    Boolean.class);
            query.setParameter("commentId", commentId);
            query.setParameter("username", username);
            return query.getSingleResult();
        }
        return false;
    }
}
