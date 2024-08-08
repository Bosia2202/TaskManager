package com.interview.taskmanager.adapters.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Task;

import java.util.List;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByTitle(String title);
}
