package com.interview.taskmanager.adapters.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.User;

import java.util.Optional;


@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    void deleteUserByUsername(String username);
}
