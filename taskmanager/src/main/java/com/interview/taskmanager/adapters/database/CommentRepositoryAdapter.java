package com.interview.taskmanager.adapters.database;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.common.dto.CommentDetails;

@Repository
public interface CommentRepositoryAdapter {

    void createComment(Comment comment);

    void updateComment(Integer id, CommentDetails commentDetails);

    void removeComment(Integer id);

    List<Comment> getCommentsByTaskId(Integer id);

}
