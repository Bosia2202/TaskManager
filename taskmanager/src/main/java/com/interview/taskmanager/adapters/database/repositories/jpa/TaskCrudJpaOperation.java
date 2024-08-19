package com.interview.taskmanager.adapters.database.repositories.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.Task;

@Repository
public interface TaskCrudJpaOperation extends JpaRepository<Task, Integer> {
    List<Task> findAllByTitle(String title, Pageable pageable);
}
