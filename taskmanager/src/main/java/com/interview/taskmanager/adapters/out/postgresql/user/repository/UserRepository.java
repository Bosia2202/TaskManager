package com.interview.taskmanager.adapters.out.postgresql.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.in.springsecurity.AuthenticateUser;
import com.interview.taskmanager.adapters.in.springsecurity.UserDetailsService;
import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.application.ports.out.UserPort;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class UserRepository implements UserPort, UserDetailsService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean create(String email, String defaultAvatarUrl, String username, String password, Role role) {
        if (isUserEmailExist(email)) {
            return false;
        }
        User user = new User();
        user.setEmail(email);
        user.setAvatarUrl(defaultAvatarUrl);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        entityManager.persist(user);
        return true;
    }

    private boolean isUserEmailExist(String email) {
        StringBuilder strBuilder = new StringBuilder();
        String query = strBuilder.append("SELECT CASE WHEN EXISTS ")
                .append("(SELECT 1 FROM User u WHERE u.email = :email) ")
                .append("THEN TRUE ELSE FALSE END").toString();
        TypedQuery<Boolean> typedQuery = entityManager.createQuery(query, Boolean.class);
        typedQuery.setParameter("email", email);
        return typedQuery.getSingleResult();
    }

    @Override
    public Optional<DatabaseUserDto> getUserById(Integer userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(new DatabaseUserDto(user.getId(), user.getEmail(), user.getAvatarUrl(), user.getUsername(),
                user.getPassword(), user.getRole()));
    }

    @Override
    public List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber, Integer pageSize) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username",
                User.class);
        query.setParameter("username", username);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultStream()
                .map(u -> new DatabaseUserDto(u.getId(), u.getEmail(), u.getAvatarUrl(), u.getUsername(),
                        u.getPassword(), u.getRole()))
                .toList();
    }

    @Override
    public String getAvatarUrl(Integer userId) {
        TypedQuery<String> query = entityManager.createQuery("SELECT u.avatarUrl FROM User u WHERE u.id = :id",
                String.class);
        query.setParameter("id", userId);
        return query.getSingleResult();
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
    public void updateAvatarUrl(String newAvatarUrl, Integer userId) {
        User user = entityManager.find(User.class, userId);
        user.setAvatarUrl(newAvatarUrl);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void updateUsername(String newUsername, Integer userId) {
        User user = entityManager.find(User.class, userId);
        user.setUsername(newUsername);
        entityManager.flush();
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
    public boolean remove(Integer userId) {
        if (!isUserExist(userId)) {
            return false;
        }
        String query = "DELETE FROM User u WHERE u.id = :userId";
        entityManager.createQuery(query)
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

    @Override
    public AuthenticateUser loadUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByEmail'");
    }

    @Override
    public boolean create(com.interview.taskmanager.domain.User user, String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsersByUsername'");
    }

    @Override
    public String getUsernameById(Integer authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsernameById'");
    }

    @Override
    public Optional<com.interview.taskmanager.domain.User> findByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

    @Override
    public void updateUser(com.interview.taskmanager.domain.User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public DatabaseUserDto getUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }

    // TODO: Сделать методы обновления boolean и исправить domain логику под них путем добавления ошибок, так же сделать рефакторинг кода по образцу TaskRepository
}
