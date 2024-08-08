package com.interview.taskmanager.adapters.database.repositories;

import org.springframework.data.jpa.repository.EntityGraph;

import com.interview.taskmanager.adapters.database.models.Comment;
import com.interview.taskmanager.common.dto.CommentDetails;

public interface CommentRepository {
    @EntityGraph(value = "comment-entity-information")
    Comment findCommentById(int id);

    void deleteCommentById(int id);

    void updateComment(int id, CommentDetails commentDetails);

    void createComment(CommentDetails commentDetails);

}
