package com.interview.taskmanager.adapters.database.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.User;

@Repository
public interface UserRepository {
    User findById(int id);
  
    @EntityGraph(value = "user-entity-with-tasks")
    User findByIdWithTasks(int id);

    @EntityGraph(value = "user-entity-with-comments")
    User findByIdWithComments(int id);

    User findByUsername(String username);

    void deleteUserById(int id);

    void deleteUserByUsername(String name);

    void updateUserUsername(int id, String newUsername);

    void updateUserEmail(int id, String newEmail);

    void updateUserPassword(int id, char[] newPassword);

    void createUser(User user);

}
