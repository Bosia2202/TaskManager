package com.interview.taskmanager.infra.postgresql.models;

import java.util.List;
import java.util.Set;

import com.interview.taskmanager.domain.taskmanager.task.TaskPriority;
import com.interview.taskmanager.domain.taskmanager.task.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "task-entity-graph", attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("title"),
                @NamedAttributeNode("description"),
                @NamedAttributeNode("status"),
                @NamedAttributeNode("priority"),
                @NamedAttributeNode(value = "author", subgraph = "user-subgraph"),
                @NamedAttributeNode(value = "executors", subgraph = "executors-subgraph"),
                @NamedAttributeNode(value = "comments", subgraph = "comment-subgraph")
        }, subgraphs = {
                @NamedSubgraph(name = "user-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("username")}) ,
                @NamedSubgraph(name = "executors-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("username")}),
                @NamedSubgraph(name = "comment-subgraph", attributeNodes = {
                        @NamedAttributeNode("content"),
                        @NamedAttributeNode(value = "author", subgraph = "user-subgraph")})
        })
})

class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    private TaskStatus status;

    @Enumerated(value = EnumType.ORDINAL)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToMany
    @JoinTable(name = "tasks_executors", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "executor_id"))
    private Set<User> executors;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<Comment> comments;

}
