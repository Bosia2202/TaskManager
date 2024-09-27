package com.interview.taskmanager.adapters.out.postgresql.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.application.dto.DatabaseTaskDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class TaskRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(DatabaseTaskDto databaseTaskDto) {
        Task task = new Task(databaseTaskDto.title(), databaseTaskDto.description(), databaseTaskDto.status(), databaseTaskDto.priority(), databaseTaskDto.authorId());
        entityManager.persist(task);
    }

    public Optional<DatabaseTaskDto> getTaskById(Integer taskId) {
        Task task = entityManager.find(Task.class, taskId);
        DatabaseTaskDto databaseTaskDto = new DatabaseTaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(), task.getAuthorId());        
        return Optional.ofNullable(databaseTaskDto);
    }

    public List<DatabaseTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.title = :title", Task.class);
        query.setParameter("title", title);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultStream().map(t -> new DatabaseTaskDto(t.getId(), t.getTitle(), t.getDescription(), 
            t.getStatus(), t.getPriority(), t.getAuthorId())).toList();
    }

    public List<DatabaseTaskDto> getAllCustomTasksByAuthorId(Integer userId) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.authorId = :userId", Task.class);
        query.setParameter("userId", userId);
        return query.getResultStream().map(t -> new DatabaseTaskDto(t.getId(), t.getTitle(), t.getDescription(), t.getStatus(), t.getPriority(), t.getAuthorId()))
            .toList();
    }

    public List<DatabaseTaskDto> getAllExecuteTaskByUserId(Integer userId) {
        TypedQuery<Integer> getTaskIdsQuery = entityManager.createQuery("SELECT e.taskId FROM TaskExecutors e WHERE e.executorId = :userId", Integer.class);
        getTaskIdsQuery.setParameter("userId", userId);
        List<Integer> taskIds = getTaskIdsQuery.getResultList();
        TypedQuery<Task> query = entityManager.createQuery("SELECT t Task t WHERE t.id IN :taskIds", Task.class);
        query.setParameter("taskIds", taskIds);
        return query.getResultStream().map(t -> new DatabaseTaskDto(t.getId(), t.getTitle(), t.getDescription(), t.getStatus(), t.getPriority(), t.getAuthorId()))
            .toList();
    }

    public Integer getAuthorId(Integer taskId) {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT t.authorId FROM Task t WHERE t.id = :taskId", Integer.class); 
        query.setParameter("taskId", taskId);
        return query.getSingleResult();
    }

    @Transactional
    public void update(DatabaseTaskDto databaseTaskDto) {
        Task task = entityManager.find(Task.class, databaseTaskDto.id());
        if(task == null) {
            throw new NoResultException();
        }
        if(!task.getTitle().equals(databaseTaskDto.title())) {
            task.setTitle(databaseTaskDto.title());
        }
        if(!task.getDescription().equals(databaseTaskDto.description())) {
            task.setDescription(databaseTaskDto.description());
        }
        if(!task.getStatus().equals(databaseTaskDto.status())) {
            task.setStatus(databaseTaskDto.status());
        }
        if(!task.getPriority().equals(databaseTaskDto.priority())) {
            task.setPriority(databaseTaskDto.priority());
        }
        if(!task.getAuthorId().equals(databaseTaskDto.authorId())) {
            task.setAuthorId(databaseTaskDto.authorId());
        }
        entityManager.merge(task);
    }

    @Transactional
    public boolean remove(Integer taskId) {
        Task task = entityManager.find(Task.class, taskId);
        if (task == null) {
            return false;
        }
        entityManager.remove(task);
        return true;
    }

    @Transactional
    public boolean addExecutor(Integer executorId, Integer taskId) {
        try{
            entityManager.createQuery("INSERT INTO TaskExecutors (taskId, executorId) VALUES (:taskId, :executorId)")
                .setParameter("taskId", taskId)
                .setParameter("executorId", executorId)
                .executeUpdate();
            return true;
        } catch (Exception e) {
            log.error("Executor wasn't added...\n", e.getCause());
            return false;
        }
    }

    @Transactional
    public boolean removeExecutor(Integer executorId, Integer taskId) {
        try{
            entityManager.createQuery("DELETE FROM TaskExecutors e WHERE e.taskId = :taskId AND e.executorId = :executorId")
                .setParameter("taskId", taskId)
                .setParameter("executorId", executorId)
                .executeUpdate();
            return true;
        } catch (Exception e) {
            log.error("Executor wasn't deleted...\n", e.getCause());
            return false;
        
    }

}
}
