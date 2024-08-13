package com.interview.taskmanager.adapters.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.UserRepository;
import com.interview.taskmanager.adapters.database.repositories.jpa.UserJpaRepository;
import com.interview.taskmanager.common.dto.UserProfile;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetails;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetailsMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public void createUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserUsername(Integer id, String newUsername) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with id [id = %d] doesn't update. Update info [newUsername = '%s']", id, newUsername)));
        user.setUsername(newUsername);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserEmail(Integer id, String newEmail) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("User with id [id = %d] doesn't update. Update info [newEmail = '%s']", id, newEmail)));
        user.setEmail(newEmail);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(Integer id, String newPassword) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Password doesn't update"));
        user.setPassword(newPassword);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String name) {
        userJpaRepository.deleteUserByUsername(name);
    }

    @Override
    public UserProfile getUserProfile(Integer id) {
        User user = findById(id);
        return new UserProfile(user);
    }

    @Override
    @EntityGraph(value = "user-entity-authorize-graph")
    public AuthenticatedUserDetails getAuthorizationInfo(String email) {
        User foundUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with email [email = '%s'] wasn't found", email)));
        return AuthenticatedUserDetailsMapper.toAuthenticatedUserDetails(foundUser);
    }

    @Override
    public User findById(Integer id) {
        return userJpaRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User with id [id = %d] doesn't found", id)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-tasks")
    public User findByIdWithTasks(Integer id) {
        return userJpaRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User with id [id = %d] doesn't found", id)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-comments")
    public User findByIdWithComments(Integer id) {
        return userJpaRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User with id [id = %d] doesn't found", id)));
    }

    @Override
    public User findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username [username = %s] doesn't found", username)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-tasks")
    public User findByUsernameWithTask(String username) {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username [username = %s] doesn't found", username)));
    }

    @Override
    @EntityGraph(value = "user-entity-with-comments")
    public User findByUsernameWithComments(String username) {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username [username = %s] doesn't found", username)));
    }

    @Override
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with email [email = '%s'] doesn't found", email)));
    }

}
