package com.interview.taskmanager.adapters.out.postgresql.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.interview.taskmanager.application.dto.DatabaseUserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(com.interview.taskmanager.domain.User user, String role) {
        User databaseUser = new User(user.getEmail(), user.getAvatarUrl(), user.getUsername(), user.getPassword(),
                role);
        entityManager.persist(databaseUser);
    }

    public Optional<DatabaseUserDto> getUserById(Integer userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(new DatabaseUserDto(user.getId(), user.getEmail(), user.getAvatarUrl(), user.getUsername(),
                user.getPassword()));
    }

    public List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber, Integer pageSize) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username",
                User.class);
        query.setParameter("username", username);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultStream()
                .map(u -> new DatabaseUserDto(u.getId(), u.getEmail(), u.getAvatarUrl(), u.getUsername(),
                        u.getPassword()))
                .toList();
    }

    public DatabaseUserDto getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultStream().map(
                u -> new DatabaseUserDto(u.getId(), u.getEmail(), u.getAvatarUrl(), u.getUsername(), u.getPassword()))
                .findFirst().orElseThrow(() -> new NoResultException());
    }

    public String getUsernameById(Integer userId) {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT u.username FROM User u WHERE u.id = :userId", String.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    public Optional<DatabaseUserDto> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        DatabaseUserDto databaseUser = query.getResultStream().map(
                u -> new DatabaseUserDto(u.getId(), u.getEmail(), u.getAvatarUrl(), u.getUsername(), u.getPassword()))
                .findFirst().orElseThrow(() -> new NoResultException());
        return Optional.of(databaseUser);
    }

    public Optional<User> loadUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        User user = query.getSingleResult();
        return Optional.of(user);
    }

    public void update(com.interview.taskmanager.domain.User user) {
        User databaseUser = entityManager.find(User.class, user.getId());
        if (databaseUser == null) {
            throw new NoResultException();
        }
        databaseUser.setEmail(user.getEmail());
        databaseUser.setAvatarUrl(user.getAvatarUrl());
        databaseUser.setUsername(user.getUsername());
        databaseUser.setPassword(user.getPassword());
        entityManager.merge(databaseUser);
    }

    @Transactional
    public boolean remove(Integer userId) {
        if (!isUserExist(userId)) {
            return false;
        }
        entityManager.createQuery("DELETE FROM User u WHERE u.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
        return true;
    }

    private boolean isUserExist(Integer userId) {
        StringBuilder strBuilder = new StringBuilder();
        String query = strBuilder.append("SELECT CASE WHEN EXISTS ")
                .append("(SELECT 1 FROM User u WHERE u.id = :userId) ")
                .append("THEN TRUE ELSE FALSE END").toString();
        TypedQuery<Boolean> typedQuery = entityManager.createQuery(query, Boolean.class);
        typedQuery.setParameter("userId", userId);
        return typedQuery.getSingleResult();
    }

}
