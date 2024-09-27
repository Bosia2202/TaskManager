package com.interview.taskmanager.adapters.out.postgresql.comment.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.application.dto.DatabaseCommentDto;
import com.interview.taskmanager.application.dto.DatabaseCreateSubCommentDto;
import com.interview.taskmanager.application.dto.DatabaseSubCommentDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(DatabaseCommentDto databaseComment) {
        Comment comment = new Comment(databaseComment.content(), databaseComment.authorId(),
                databaseComment.taskId());
        entityManager.persist(comment);
    }

    public void saveSubComment(DatabaseCreateSubCommentDto subCommentDto) {
        Comment comment = entityManager.getReference(Comment.class, subCommentDto.commentId());
        if(comment == null) { 
            log.error(String.format("Sub comment wasn't be save, because comment [id = %d] doesn't exist", subCommentDto.commentId()));        
            throw new NoResultException();
        }
        SubComment subComment = new SubComment(subCommentDto.content(), subCommentDto.authorId(), comment);
        entityManager.persist(subComment);
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

    public DatabaseSubCommentDto getSubCommentById(Integer subCommentId) {
        TypedQuery<SubComment> query = entityManager.createQuery(
                "SELECT s FROM SubComment s JOIN FETCH s.comment WHERE s.id = :subCommentId", SubComment.class);
        query.setParameter("subCommentId", subCommentId);
        SubComment subComment = query.getSingleResult();
        return new DatabaseSubCommentDto(subComment.getId(), subComment.getContent(),
                subComment.getAuthorId(), subComment.getComment().getId());
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
