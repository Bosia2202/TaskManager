package com.interview.taskmanager.adapters.out.postgresql.comment.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sub_comments")
@Data
@NoArgsConstructor
class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public SubComment(Integer id, String content, Integer authorId, Comment comment) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
        this.comment = comment;
    }

    public SubComment(String content, Integer authorId, Comment comment) {
        this.content = content;
        this.authorId = authorId;
        this.comment = comment;
    }

}
