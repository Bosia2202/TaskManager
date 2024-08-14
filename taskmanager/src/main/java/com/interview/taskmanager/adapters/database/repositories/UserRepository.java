package com.interview.taskmanager.adapters.database.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.UserRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.jpa.UserJpaRepository;
import com.interview.taskmanager.common.dto.profile.UserProfile;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetails;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetailsMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepository implements UserRepositoryAdapter {
    private UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public void createUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserUsername(Integer id, String newUsername) throws EntityNotFoundException {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User [id = %d] wasn't update. Update info [newUsername = '%s']", id, newUsername)));
        user.setUsername(newUsername);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserEmail(Integer id, String newEmail) throws EntityNotFoundException {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("User [id = %d] wasn't update. Update info [newEmail = '%s']", id, newEmail)));
        user.setEmail(newEmail);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(Integer id, String newPassword) throws EntityNotFoundException {
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
    @EntityGraph(value = "user-entity-graph-authorize")
    @Transactional(readOnly = true)
    public AuthenticatedUserDetails getAuthorizationInfo(String email) throws EntityNotFoundException {
        User foundUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(
                String.format("User [email = '%s'] wasn't found", email)));
        return AuthenticatedUserDetailsMapper.toAuthenticatedUserDetails(foundUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Integer id) throws EntityNotFoundException {
        return userJpaRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User [id = %d] wasn't found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User [username = %s] wasn't found", username)));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(
                String.format("User [email = '%s'] wasn't found", email)));
    }

    @Override
    @EntityGraph(value = "user-entity-graph")
    @Transactional(readOnly = true)
    public UserProfile findUserProfileById(Integer id) throws EntityNotFoundException {
        User user = userJpaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User [id = %d] wasn't found", id)));
        return new UserProfile(user);
    }

    @Override
    @EntityGraph(value = "user-entity-graph")
    @Transactional(readOnly = true)
    public UserProfile findUserProfileByUsername(String username) throws EntityNotFoundException {
        User user = userJpaRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User [username = %s] wasn't found", username)));
        return new UserProfile(user);
    }

}
