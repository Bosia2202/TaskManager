package com.interview.taskmanager.infra.postgresql.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sub_comments")
@Data
class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
}
