package com.interview.taskmanager.adapters.out.postgresql.comment.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.jsonwebtoken.lang.Collections;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    private Integer authorId;

    private Integer taskId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private List<SubComment> subComments;

    public Comment(String content, Integer authorId, Integer taskId) {
        this.content = content;
        this.authorId = authorId;
        this.taskId = taskId;
        this.subComments = Collections.emptyList();
    }

}
