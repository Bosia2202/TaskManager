package com.interview.taskmanager.infra.postgresql;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "comments")
@Data
class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private List<SubComment> subComments;

}
