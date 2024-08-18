package com.interview.taskmanager.adapters.database.repositories;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void createNewTask(TaskDetails taskDetails, User currentUser) {
        Task task = new Task();
        task.setDetails(taskDetails);
        task.setAuthor(currentUser);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void updateTaskById(Integer id, TaskDetails taskDetails) throws NoResultException {
        Task task = taskJpaRepository.findById(id)
                .orElseThrow(() -> new NoResultException(
                        String.format("Task [id = '%d'] was not found and no update was performed", id)));
        cacheManager.getCache("tasks").evictIfPresent(id);
        task.setDetails(taskDetails);
        taskJpaRepository.save(task);
    }

    @Override
    @Transactional
    public void removeTaskById(Integer id) throws NoResultException {
        if (taskJpaRepository.existsById(id)) {
            cacheManager.getCache("tasks").evictIfPresent(id);
            taskJpaRepository.deleteById(id);
        } else {
            throw new NoResultException(String.format("Task [id = '%d'] not found and not deleted", id));
        }
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public TaskDto loadCompleteTaskInfoById(Integer id) throws NoResultException {
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

    private User getAuthorByTaskId(Integer id) throws NoResultException {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u JOIN FETCH u.ownerTasks ow WHERE ow.id = :taskid", User.class);
        query.setParameter("taskid", id);
        return query.getSingleResult();
    }

    private List<User> getExecutorsByTaskId(Integer id) {
        Query query = entityManager
                .createNativeQuery("SELECT executor_id FROM tasks_executors WHERE task_id = :taskId", Integer.class);
        query.setParameter("taskId", id);
        return query.getResultList().stream().map(executor_id -> getExecutorById(id)).toList();
    }

    private User getExecutorById(Integer id) throws NoResultException {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getUserOwnedTasks(Integer userId) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.author.id = :id", Task.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getUserExecutedTasks(Integer userId) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t JOIN t.executors e WHERE e.id = :id", Task.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getUserOwnedTasksByUsername(String username) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.author.username = :username", Task.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getUserExecutedTasksByUsername(String username) {
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
    @Transactional
    public void addExecutorToTask(User executor, Integer taskId) {
        Query query = entityManager
                .createNativeQuery("INSERT INTO tasks_executors (task_id,executor_id) VALUES (:taskId, :executorId)");
        query.setParameter("taskId", taskId);
        query.setParameter("executorId", executor.getId());
        query.executeUpdate();
        cacheManager.getCache("tasks").evictIfPresent(taskId);
    }

    @Override
    @Transactional
    public void removeExecutorFromTask(User executor, Integer taskId) throws EntityNotFoundException {
        Query query = entityManager.createNativeQuery(
                "DELETE FROM tasks_executors WHERE task_id = :taskId AND executor_id = :executorId", Void.class);
        query.setParameter("executorId", executor.getId());
        query.setParameter("taskId", taskId);
        query.executeUpdate();
        cacheManager.getCache("tasks").evictIfPresent(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findById(Integer id) throws NoResultException {
        return taskJpaRepository.findById(id)
                .orElseThrow(() -> new NoResultException(String.format("Task [id = '%d'] wasn't found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findTasksByTitle(String title, Integer pageNumber) {
        pageNumber = (pageNumber - 1) * 10;
        Pageable page = PageRequest.of(pageNumber, 10);
        return taskJpaRepository.findAllByTitle(title, page);
    }

}
