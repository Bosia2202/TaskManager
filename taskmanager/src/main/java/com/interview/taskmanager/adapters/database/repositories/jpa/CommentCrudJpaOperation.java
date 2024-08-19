package com.interview.taskmanager.adapters.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Comment;

@Repository
public interface CommentCrudJpaOperation extends JpaRepository<Comment, Integer>{

}
