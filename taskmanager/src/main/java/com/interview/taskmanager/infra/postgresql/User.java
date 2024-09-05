package com.interview.taskmanager.infra.postgresql;

import java.util.List;

import com.interview.taskmanager.domain.user.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "users")
@Data
@NamedEntityGraphs(value = {

        @NamedEntityGraph(name = "user-entity-graph-authorize", attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("email"),
                @NamedAttributeNode("username"),
                @NamedAttributeNode("password"),
                @NamedAttributeNode("role")
        }),

        @NamedEntityGraph(name = "user-entity-graph-with-owner-task", attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("username"),
                @NamedAttributeNode(value = "ownerTasks", subgraph = "owner-task-subgraph"),
        }, subgraphs = {
                @NamedSubgraph(name = "owner-task-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("title"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode("priority")
                }),
                @NamedSubgraph(name = "author-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("username")
                })
        })
})
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "avatar")
    private String avatarUrl;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Task> ownerTasks;

    @ManyToMany(mappedBy = "executors")
    private List<Task> executedTasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<SubComment> subComments;
}
