package com.interview.taskmanager.adapters.database.models;

import com.interview.taskmanager.common.dto.CommentDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@NamedEntityGraph(name = "comment-entity-information", attributeNodes = {
        @NamedAttributeNode("id"),
        @NamedAttributeNode("content"),
        @NamedAttributeNode(value = "author", subgraph = "author-subgraph"),
        @NamedAttributeNode(value = "task", subgraph = "task-subgraph") }, subgraphs = {
                @NamedSubgraph(name = "author-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("username"),
                }),
                @NamedSubgraph(name = "task-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("title")
                })
        })

public class Comment {
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

    public Comment(String content, User author, Task task) {
        this.content = content;
        this.author = author;
        this.task = task;
    }

    public void setDetails(CommentDetails commentDetails){
        this.content = commentDetails.getContent();
    }

}
