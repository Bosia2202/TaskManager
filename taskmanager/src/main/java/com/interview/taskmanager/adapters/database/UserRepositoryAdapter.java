package com.interview.taskmanager.adapters.database;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetails;

import jakarta.persistence.NoResultException;

@Repository
public interface UserRepositoryAdapter {

    void createUser(User user);

    void updateUsername(Integer id, String newUsername) throws NoResultException;

    void updateEmail(Integer id, String newEmail) throws NoResultException;

    void updatePassword(Integer id, String newPassword) throws NoResultException;

    void removeUserById(Integer id) throws NoResultException;

    AuthenticatedUserDetails getUserAuthorizationInfo(String email) throws UsernameNotFoundException;

    User findById(Integer id) throws NoResultException;

    User findByUsername(String username) throws NoResultException;

    User findByEmail(String email) throws NoResultException;

    User findUserWithAssignedTasksByUsername(String username);

}