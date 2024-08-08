package com.interview.taskmanager.adapters.database.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "user-entity-with-tasks", attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("username"),
                @NamedAttributeNode(value = "ownerTasks", subgraph = "task-subgraph"),
                @NamedAttributeNode(value = "executedTask", subgraph = "task-subgraph"),
        }, subgraphs = {
                @NamedSubgraph(name = "task-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("title"),
                        @NamedAttributeNode("description"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode("priority")
                })
        }),
        @NamedEntityGraph(name = "user-entity-with-comments", attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("username"),
                @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph")
        }, subgraphs = {
                @NamedSubgraph(name = "comments-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("content")
                })
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private char[] password;
    @OneToMany(mappedBy = "author")
    List<Task> ownerTasks;
    @ManyToMany(mappedBy = "executors")
    List<Task> executedTasks;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

}
