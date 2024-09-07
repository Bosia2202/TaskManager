package com.interview.taskmanager.infra.postgresql;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.TaskRepository;
import com.interview.taskmanager.domain.task.BriefTaskDto;
import com.interview.taskmanager.domain.task.TaskAuthorDto;
import com.interview.taskmanager.domain.task.TaskDto;
import com.interview.taskmanager.domain.task.TaskPresentationDto;
import com.interview.taskmanager.domain.task.TaskPriority;
import com.interview.taskmanager.domain.task.TaskStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class PostgresTaskRepository implements TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(TaskDto taskDto, Integer authorId) {
        Task task = new Task();
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());
        task.setPriority(taskDto.priority());
        try {
            User authorProxy = entityManager.getReference(User.class, authorId);
            task.setAuthor(authorProxy);
            entityManager.persist(task);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<TaskPresentationDto> getTaskById(Integer taskId) {
        Optional<Task> taskOptional = Optional.ofNullable(entityManager.find(Task.class, taskId));
        if (taskOptional.isEmpty()) {
            return Optional.empty();
        }
        Task task = taskOptional.get();
        return Optional.of(new TaskPresentationDto(task.getTitle(), task.getDescription(), task.getStatus(),
                task.getPriority(), getTaskAuthor(taskId)));
    }

    private TaskAuthorDto getTaskAuthor(Integer taskId) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT t.author.id, t.author.username FROM Task t WHERE t.id = :taskId",
                Object[].class);
        Object[] response = query.setParameter("taskId", taskId).getSingleResult();
        Integer authorId = (Integer) response[0];
        String username = (String) response[1];
        return new TaskAuthorDto(authorId, username);
    }

    @Override
    public List<BriefTaskDto> getTasksByTitle(String title, Integer pageNumber, Integer pageSize) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.title = :title", Task.class);
        query.setParameter("title", title)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        return query.getResultStream().map(t -> new BriefTaskDto(t.getTitle(), t.getStatus(), t.getPriority(),
                getTaskAuthor(t.getId()))).toList();
    }

    @Override
    public List<BriefTaskDto> getCustomTaskByUserId(Integer userId) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.author.id = :userId",
                Task.class);
        return query.setParameter("userId", userId).getResultStream()
                .map(t -> new BriefTaskDto(t.getTitle(), t.getStatus(), t.getPriority(),
                        getTaskAuthor(t.getId())))
                .toList();

    }

    @Override
    public List<BriefTaskDto> getExecutingTasksByUserId(Integer userId) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.executors.id = :userId",
                Task.class);
        return query.setParameter("userId", userId).getResultStream()
                .map(t -> new BriefTaskDto(t.getTitle(), t.getStatus(), t.getPriority(),
                        getTaskAuthor(t.getId())))
                .toList();
    }

    @Override
    public boolean updateTitle(String newTitle, Integer taskId) {
        try {
            Task task = entityManager.find(Task.class, taskId);
            task.setTitle(newTitle);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateDescription(String newDescription, Integer taskId) {
        try {
            Task task = entityManager.find(Task.class, taskId);
            task.setDescription(newDescription);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateStatus(TaskStatus newStatus, Integer taskId) {
        try {
            Task task = entityManager.find(Task.class, taskId);
            task.setStatus(newStatus);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatePriority(TaskPriority newPriority, Integer taskId) {
        try {
            Task task = entityManager.find(Task.class, taskId);
            task.setPriority(newPriority);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(Integer taskId) {
        if (!isTaskExist(taskId)) {
            return false;
        }
        entityManager.createQuery("DELETE FROM Task t WHERE t.id = :taskId")
                .setParameter("taskId", taskId)
                .executeUpdate();
        return true;
    }

    private boolean isTaskExist(Integer taskId) {
        StringBuilder strBuilder = new StringBuilder();
        String query = strBuilder.append("SELECT CASE WHEN EXISTS ")
                .append("(SELECT 1 FROM Task t WHERE t.id = :taskId) ")
                .append("THEN TRUE ELSE FALSE END").toString();
        TypedQuery<Boolean> typedQuery = entityManager.createQuery(query, Boolean.class);
        return typedQuery.setParameter("taskId", taskId).getSingleResult();
    }

    @Override
    public boolean addExecutor(Integer executorId, Integer taskId) {
        try {
            User executorProxy = entityManager.getReference(User.class, executorId);
            Task task = entityManager.find(Task.class, taskId);
            task.getExecutors().add(executorProxy);
            entityManager.merge(task);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeExecutor(Integer executorId, Integer taskId) {
        try {
            User executorProxy = entityManager.getReference(User.class, executorId);
            Task task = entityManager.find(Task.class, taskId);
            task.getExecutors().remove(executorProxy);
            entityManager.merge(task);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
