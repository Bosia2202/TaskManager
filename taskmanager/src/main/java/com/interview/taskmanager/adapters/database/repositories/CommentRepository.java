package com.interview.taskmanager.adapters.database.repositories;

import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.common.dto.CommentDetails;

public interface CommentRepository {
    Comment findCommentById(int commentId);

    void deleteComment(Comment comment);

    void updateComment(int commentId, CommentDetails commentDetails);

    void createComment(Comment comment);

}
