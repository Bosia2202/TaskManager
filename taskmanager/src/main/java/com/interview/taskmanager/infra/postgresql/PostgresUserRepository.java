package com.interview.taskmanager.infra.postgresql;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.DatabaseUserDto;
import com.interview.taskmanager.adapters.database.UserRepository;
import com.interview.taskmanager.domain.user.BriefUserInfo;
import com.interview.taskmanager.domain.user.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(String email, String defaultAvatarUrl, String username, String password, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setAvatarUrl(defaultAvatarUrl);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUsername(String newUsername, Integer userId) {
        User user = entityManager.find(User.class, userId);
        user.setUsername(newUsername);
        entityManager.flush();
    }

    @Override
    public String getPassword(Integer userId) {
        String query = "SELECT u.password FROM User u WHERE u.id = :userId";
        TypedQuery<String> request = entityManager.createQuery(query, String.class);
        request.setParameter("userId", userId);
        return request.getSingleResult();
    }

    @Override
    @Transactional
    public void updatePassword(String encryptedNewPassword, Integer userId) {
        User user = entityManager.find(User.class, userId);
        user.setPassword(encryptedNewPassword);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void remove(Integer userId) {
        User user = entityManager.find(User.class, userId);
        entityManager.remove(user);
    }

    @Override
    public DatabaseUserDto getUserById(Integer userId) {
        User user = entityManager.find(User.class, userId);
        return new DatabaseUserDto(user.getId(), user.getEmail(), user.getAvatarUrl(), user.getUsername(),
                user.getPassword(), user.getRole());
    }

    @Override
    public List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber, Integer pageSize) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultStream().map(u -> new BriefUserInfo(u.getId(), u.getAvatarUrl(), u.getUsername()))
                .toList();
    }

    @Override
    public String getAvatarUrl(Integer userId) {
        TypedQuery<String> query = entityManager.createQuery("SELECT u.avatarUrl FROM User u WHERE u.id = :id", String.class);
        query.setParameter("id", userId);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateAvatarUrl(String newAvatarUrl, Integer userId) {
        User user = entityManager.find(User.class, userId);
        user.setAvatarUrl(newAvatarUrl);
        entityManager.flush();
    }

}
