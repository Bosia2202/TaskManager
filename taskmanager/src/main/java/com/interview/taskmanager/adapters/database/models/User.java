package com.interview.taskmanager.adapters.database.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.interview.taskmanager.adapters.security.Role;

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
public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Column(unique = true, nullable = false, length = 50)
        private String username;

        @Column(unique = true, nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @Enumerated(value = EnumType.STRING)
        @Column(nullable = false)
        private Role role;

        @OneToMany(mappedBy = "author")
        private transient List<Task> ownerTasks;

        @ManyToMany(mappedBy = "executors")
        private transient List<Task> executedTasks;

        @OneToMany(mappedBy = "author")
        private transient List<Comment> comments;

        private String jwtToken;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(role.name()));
        }

}
