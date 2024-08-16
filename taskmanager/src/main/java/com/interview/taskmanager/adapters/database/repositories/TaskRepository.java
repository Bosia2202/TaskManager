package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.TaskRepositoryAdapter;
import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.jpa.TaskJpaRepository;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.common.dto.task.TaskDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TaskRepository implements TaskRepositoryAdapter {

    private final TaskJpaRepository taskJpaRepository;

    private final CacheManager cacheManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(TaskDetails taskDetails, User currentUser) {
        Task task = new Task();
        task.setDetails(taskDetails);
        task.setAuthor(currentUser);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void updateById(Integer id, TaskDetails taskDetails) throws EntityNotFoundException {
        Task task = taskJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task [id = '%d'] wasn't found", id)));
        cacheManager.getCache("tasks").evictIfPresent(id);
        task.setDetails(taskDetails);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        cacheManager.getCache("tasks").evictIfPresent(id);
        taskJpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addExecutor(User executer, Task task) {
        task.addExecutor(executer);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteExecutor(Integer executerId, Integer taskId) throws EntityNotFoundException {
        Query query = entityManager.createNativeQuery(
                "DELETE FROM tasks_executors WHERE task_id = :taskId AND executor_id = :executorId", Void.class);
        query.setParameter("executorId", executerId);
        query.setParameter("taskId", taskId);
        query.executeUpdate();
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    @Transactional(readOnly = true)
    public Task findById(Integer id) throws EntityNotFoundException {
        return taskJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task [id = '%d'] wasn't found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllByTitle(String title) {
        return taskJpaRepository.findAllByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getOwnerTasksByUserId(Integer id) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.author.id = :id", Task.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getExecutedTasksByUserId(Integer id) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t JOIN t.executors e WHERE e.id = :id", Task.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getOwnerTasksByUsername(String username) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.author.username = :username", Task.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getExecutedTasksByUsername(String username) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t JOIN t.executors e WHERE e.username = :username", Task.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserOwnerOfTask(String username, Integer taskId) {
        TypedQuery<Boolean> query = entityManager.createQuery(
                "SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Task t JOIN t.author u WHERE u.username = :username AND t.id = :taskId",
                Boolean.class);
        query.setParameter("username", username);
        query.setParameter("taskId", taskId);
        return query.getSingleResult();
    }

    @Override
    public TaskDto loadFullTaskInfoById(Integer id) {
        Task task = taskJpaRepository.findById(id)
                .orElseThrow(() -> new NoResultException(String.format("Task [id = '%d'] wasn't found", id)));
        User author = getAuthorByTaskId(id);
        List<User> executors = getExecutorsByTaskId(id);
        return new TaskDto.Builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .author(author)
                .executors(executors)
                .build();
    }

    private User getAuthorByTaskId(Integer id) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u JOIN FETCH u.ownerTasks ow WHERE ow.id = :taskid", User.class);
        query.setParameter("taskid", id);
        return query.getSingleResult();
    }

    private List<User> getExecutorsByTaskId(Integer id) {
        Query query = entityManager
                .createNativeQuery("SELECT executor_id FROM tasks_executors WHERE task_id = :task_id", Integer.class);
        query.setParameter("task_id", id);
        return query.getResultList().stream().map(executer_id -> getExecutorById((Integer) executer_id)).toList();
    }

    private User getExecutorById(Integer id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
