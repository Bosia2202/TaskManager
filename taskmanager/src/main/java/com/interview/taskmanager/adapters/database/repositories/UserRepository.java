package com.interview.taskmanager.adapters.database.repositories;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.UserRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.jpa.UserCrudJpaOperation;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetails;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetailsMapper;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class UserRepository implements UserRepositoryAdapter {

    private final UserCrudJpaOperation userJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUsername(Integer id, String newUsername) throws NoResultException {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new NoResultException(String.format(
                        "The user [id = '%d'] has not been found and their name has not been updated.", id)));
        user.setUsername(newUsername);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updateEmail(Integer id, String newEmail) throws NoResultException {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new NoResultException(String
                        .format("The user [id = '%d'] has not been found and their email has not been updated.", id)));
        user.setEmail(newEmail);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(Integer id, String newPassword) throws NoResultException {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new NoResultException(String
                        .format("The user [id = '%d'] has not been found and their password has not been updated.", id)));
        user.setPassword(newPassword);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUserById(Integer id) throws NoResultException {
        if (userJpaRepository.existsById(id)) {
            userJpaRepository.deleteById(id);
        } else {
            throw new NoResultException(String.format("The user [id = '%d'] has not been found and has not been deleted.", id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticatedUserDetails getUserAuthorizationInfo(String email) throws UsernameNotFoundException {
        User foundUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("The user [email = '%s'] wasn't found and has no been authenticated.", email)));
        return AuthenticatedUserDetailsMapper.toAuthenticatedUserDetails(foundUser);
    }

    @Override
    public User findById(Integer id) throws NoResultException {
        return userJpaRepository.findById(id)
                .orElseThrow(
                        () -> new NoResultException(String.format("User [id = '%d'] wasn't found.", id)));
    }

    @Override
    public User findByUsername(String username) throws NoResultException {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new NoResultException(
                        String.format("User [username = %s] wasn't found.", username)));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) throws NoResultException {
        return userJpaRepository.findByEmail(email).orElseThrow(() -> new NoResultException(
                String.format("User [email = '%s'] wasn't found.", email)));
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

}
