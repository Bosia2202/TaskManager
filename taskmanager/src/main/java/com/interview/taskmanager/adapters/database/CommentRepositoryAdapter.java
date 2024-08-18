package com.interview.taskmanager.adapters.database;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.comment.CommentDto;

import jakarta.persistence.NoResultException;

@Repository
public interface CommentRepositoryAdapter {

    void createComment(Comment comment);

    void updateComment(Integer id, CommentDetails commentDetails) throws NoResultException;

    void removeComment(Integer id) throws NoResultException;

    List<CommentDto> getCommentsByTaskId(Integer id, Integer pageNumber);

    boolean isUsersComment(String username,Integer commentId);

}
