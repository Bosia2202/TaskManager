package com.interview.taskmanager.adapters.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.UserRepository;
import com.interview.taskmanager.adapters.database.repositories.jpa.UserJpaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private UserJpaRepository repository;

    @Override
    @EntityGraph(value = "user-entity-with-tasks")
    public User findByIdWithTasks(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id" + id + "doesn't found"));
    }

    @Override
    @EntityGraph(value = "user-entity-with-comments")
    public User findByIdWithComments(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id" + id + "doesn't found"));
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' does't found"));
    }

    @Override
    @EntityGraph(value = "user-entity-with-tasks")
    public User findByUsernameWithTask(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' does't found"));
    }

    @Override
    @EntityGraph(value = "user-entity-with-comments")
    public User findByUsernameWithComments(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username '" + username + "' does't found"));
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
    @Transactional
    public void updateUserUsername(int id, String newUsername) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '" + id + "' not found"));
        user.setUsername(newUsername);
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUserEmail(int id, String newEmail) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '" + id + "' not found"));
        user.setEmail(newEmail);
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(int id, String newPassword) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '" + id + "' not found"));
        user.setPassword(newPassword);
        repository.save(user);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        repository.save(user);
    }

    @Override
    public User findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id" + id + "doesn't found"));
    }

}
