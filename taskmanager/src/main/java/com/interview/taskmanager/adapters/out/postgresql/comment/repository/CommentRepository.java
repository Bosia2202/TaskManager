package com.interview.taskmanager.adapters.out.postgresql.comment.repository;

import java.util.List;

import com.interview.taskmanager.application.dto.DatabaseCommentDto;
import com.interview.taskmanager.application.dto.DatabaseSubCommentDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(DatabaseCommentDto databaseComment) {
        Comment comment = new Comment(databaseComment.content(), databaseComment.authorId(),
                databaseComment.taskId());
        entityManager.persist(comment);
    }

    public void saveSubComment(DatabaseCommentDto databaseComment, Integer commentId) {
        if (databaseComment.id() == null) {
            Comment comment = entityManager.getReference(Comment.class, commentId);
            SubComment subComment = new SubComment(databaseComment.content(), databaseComment.authorId(), comment);
            entityManager.persist(subComment);
        } else {
            SubComment subComment = entityManager.find(SubComment.class, databaseComment.id());
            if (subComment == null) {
                throw new NoResultException("Sub comment wasn't found");
            }
            subComment.setContent(databaseComment.content());
            entityManager.merge(subComment);
        }
    }

    public List<DatabaseCommentDto> getCommentsByTaskId(Integer taskId, Integer pageNumber, Integer pageSize) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "SELECT c FROM Comment c WHERE c.taskId = :taskId", Comment.class);
        query.setParameter("taskId", taskId)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        return query.getResultStream()
                .map(comment -> new DatabaseCommentDto(comment.getId(), comment.getContent(), comment.getAuthorId(),
                        taskId))
                .toList();
    }

    public List<DatabaseSubCommentDto> getSubCommentsByCommentId(Integer commentId, Integer pageNumber,
            Integer pageSize) {
        TypedQuery<SubComment> query = entityManager.createQuery(
                "SELECT sc FROM SubComment sc WHERE sc.comment.id = :commentId", SubComment.class);
        query.setParameter("commentId", commentId)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        return query.getResultStream()
                .map(comment -> new DatabaseSubCommentDto(comment.getId(), comment.getContent(), comment.getAuthorId(),
                        commentId))
                .toList();
    }

    public DatabaseCommentDto getCommentById(Integer commentId) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "SELECT c FROM Comment c WHERE c.id = :commentId", Comment.class);
        query.setParameter("commentId", commentId);
        Comment comment = query.getSingleResult();
        return new DatabaseCommentDto(comment.getId(), comment.getContent(),
                comment.getAuthorId(), comment.getTaskId());
    }

    public Integer getAuthorIdByCommentId(Integer commentId) {
        TypedQuery<Integer> query = entityManager
                .createQuery("SELECT c.authorId FROM Comment c WHERE c.id = :commentId", Integer.class);
        query.setParameter("commentId", commentId);
        return query.getSingleResult();
    }

    public void update(DatabaseCommentDto databaseComment) {
        Comment comment = entityManager.find(Comment.class, databaseComment.id());
        if (comment == null) {
            throw new NoResultException("Comment wasn't found");
        }
        comment.setContent(databaseComment.content());
        entityManager.merge(comment);
    }

    public void removeById(Integer commentId) {
        Comment comment = entityManager.find(Comment.class, commentId);
        if (comment == null) {
            throw new NoResultException("No comment found and not deleted.");
        }
        entityManager.remove(commentId);
    }

    public void removeSubCommentById(Integer subCommentId) {
        SubComment subComment = entityManager.find(SubComment.class, subCommentId);
        if (subComment == null) {
            throw new NoResultException("No sub comment found and not deleted.");
        }
        entityManager.remove(subComment);
    }

}
