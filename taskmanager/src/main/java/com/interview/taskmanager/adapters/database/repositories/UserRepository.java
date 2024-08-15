package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.UserRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.jpa.UserJpaRepository;
import com.interview.taskmanager.common.dto.profile.UserProfile;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetails;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetailsMapper;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class UserRepository implements UserRepositoryAdapter {

    private final UserJpaRepository userJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
    @Transactional(readOnly = true)
    public AuthenticatedUserDetails getAuthorizationInfo(String email) throws EntityNotFoundException {
        User foundUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(
                String.format("User [email = '%s'] wasn't found", email)));
        return AuthenticatedUserDetailsMapper.toAuthenticatedUserDetails(foundUser);
    }

    @Override
    public User findById(Integer id) throws EntityNotFoundException {
        return userJpaRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User [id = %d] wasn't found", id)));
    }

    @Override
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
    @Transactional(readOnly = true)
    public UserProfile findUserProfileById(Integer id) throws NoResultException {
        User user = userJpaRepository.findById(id).orElseThrow(() -> new NoResultException());
        List<Task> ownerTasks = getOwnerTasksByUserId(id);
        List<Task> executedTasks = getExecutedTasksByUserId(id);
        return new UserProfile(user, ownerTasks, executedTasks);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfile findUserProfileByUsername(String username) throws NoResultException {
        User user = userJpaRepository.findByUsername(username).orElseThrow(() -> new NoResultException());
        List<Task> ownerTasks = getOwnerTasksByUsername(username);
        List<Task> executedTasks = getExecutedTasksByUsername(username);
        return new UserProfile(user, ownerTasks, executedTasks);
    }

    @Override
    public User findUserWithAssignedTasksByUsername(String username) throws NoResultException {
        EntityGraph entityGraph = entityManager.getEntityGraph("user-entity-graph-with-owner-task");
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username",
                User.class);
        query.setParameter("username", username);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        return query.getSingleResult();
    }

    private List<Task> getOwnerTasksByUserId(Integer id) {
        TypedQuery<Task> ownerTasksQuery = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.author.id = :id", Task.class);
        ownerTasksQuery.setParameter("id", id);
        return ownerTasksQuery.getResultList();
    }

    private List<Task> getExecutedTasksByUserId(Integer id) {
        TypedQuery<Task> executedTasksQuery = entityManager.createQuery(
                "SELECT t FROM Task t JOIN FETCH t.executors e WHERE e.id = :id", Task.class);
        executedTasksQuery.setParameter("id", id);
        return executedTasksQuery.getResultList();
    }

    private List<Task> getOwnerTasksByUsername(String username) {
        TypedQuery<Task> ownerTasksQuery = entityManager.createQuery(
            "SELECT t FROM Task t WHERE t.author.username = :username", Task.class);
        ownerTasksQuery.setParameter("username", username);
        return ownerTasksQuery.getResultList();
    }

    private List<Task> getExecutedTasksByUsername(String username) {
        TypedQuery<Task> executedTasksQuery = entityManager.createQuery(
            "SELECT t FROM Task t JOIN FETCH t.executors e WHERE e.username = :username", Task.class);
        executedTasksQuery.setParameter("username", username);
        return executedTasksQuery.getResultList();
    }

}
