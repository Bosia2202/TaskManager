package com.interview.taskmanager.adapters.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.UserRepository;
import com.interview.taskmanager.adapters.database.repositories.jpa.UserJpaRepository;
import com.interview.taskmanager.common.dto.UserProfile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private UserJpaRepository repository;

    @Override
    @Transactional
    public void createUser(User user) {
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUserUsername(int id, String newUsername) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with id [id = %d] doesn't update. Update info [newUsername = '%s']", id, newUsername)));
        user.setUsername(newUsername);
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUserEmail(int id, String newEmail) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("User with id [id = %d] doesn't update. Update info [newEmail = '%s']", id, newEmail)));
        user.setEmail(newEmail);
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(int id, String newPassword) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Password doesn't update"));
        user.setPassword(newPassword);
        repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String name) {
        repository.deleteUserByUsername(name);
    }

    @Override
    public User findById(int id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User with id [id = %d] doesn't found", id)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-tasks")
    public User findByIdWithTasks(int id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User with id [id = %d] doesn't found", id)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-comments")
    public User findByIdWithComments(int id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User with id [id = %d] doesn't found", id)));
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username [username = %s] doesn't found", username)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-tasks")
    public User findByUsernameWithTask(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username [username = %s] doesn't found", username)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-comments")
    public User findByUsernameWithComments(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username [username = %s] doesn't found", username)));
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with email [email = '%s'] doesn't found", email)));
    }

    @Override
    public UserProfile getUserProfile(Integer id) {
        User user = findById(id);
        return new UserProfile(user);
    }

}
