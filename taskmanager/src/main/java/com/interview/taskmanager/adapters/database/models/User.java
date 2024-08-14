package com.interview.taskmanager.adapters.database.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.interview.taskmanager.infra.security.Role;

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
@Data
@Table(name = "users")
@NamedEntityGraphs(value = {
        
        @NamedEntityGraph(name = "user-entity-graph-authorize", attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("email"),
                @NamedAttributeNode("username"),
                @NamedAttributeNode("password"),
                @NamedAttributeNode("role")
        }),

        @NamedEntityGraph(name = "user-entity-graph", attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("username"),
                @NamedAttributeNode(value = "ownerTasks", subgraph = "owner-task-subgraph"),
                @NamedAttributeNode(value = "executedTasks", subgraph = "executed-task-subgraph")
        }, subgraphs = {
                @NamedSubgraph(name = "owner-task-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("title"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode("priority")
                }),
                @NamedSubgraph(name = "executed-task-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("title"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode("priority"),
                        @NamedAttributeNode(value = "author", subgraph = "author-subgraph")
                }),
                @NamedSubgraph(name = "author-subgraph", attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("username")
                })
        })
})
public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Column(unique = true, nullable = false)
        private String email;

        @Column(unique = true, nullable = false, length = 50)
        private String username;

        @Column(nullable = false)
        private String password;

        @Enumerated(value = EnumType.STRING)
        @Column(nullable = false)
        private Role role;

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
        private List<Task> ownerTasks;

        @ManyToMany(cascade = CascadeType.ALL, mappedBy = "executors")
        private List<Task> executedTasks;

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
        private List<Comment> comments;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(role.name()));
        }

}
