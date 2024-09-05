package com.interview.taskmanager.infra.postgresql;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interview.taskmanager.adapters.database.SimpleProfileInfo;
import com.interview.taskmanager.adapters.database.UserRepository;
import com.interview.taskmanager.domain.task.BriefInformationTaskDto;
import com.interview.taskmanager.domain.user.BriefUserInfo;
import com.interview.taskmanager.domain.user.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public void create(String email, String defaultAvatarUrl, String username, String password, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setAvatarUrl(defaultAvatarUrl);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUsername(String newUsername, Integer userId) {
        User user = entityManager.find(User.class, userId);
        user.setUsername(newUsername);
        entityManager.flush();
    }

    @Override
    public String getPassword(Integer userId) {
        String query = "SELECT u.password FROM User u WHERE u.id = :userId";
        TypedQuery<String> request = entityManager.createQuery(query, String.class);
        request.setParameter("userId", userId);
        return request.getSingleResult();
    }

    @Override
    @Transactional
    public void updatePassword(String encryptedNewPassword, Integer userId) {
        User user = entityManager.find(User.class, userId);
        user.setPassword(encryptedNewPassword);
        entityManager.flush();
    }

    @Override
    public void remove(Integer userId) {
        User user = entityManager.find(User.class, userId);
        entityManager.remove(user);
    }

    @Override
    public SimpleProfileInfo getSimpleProfileInfo(Integer userId) {
        User user = entityManager.find(User.class, userId);
        return new SimpleProfileInfo(user.getId(), user.getAvatarUrl(), user.getUsername());
    }

    @Override
    public List<BriefInformationTaskDto> getUserCustomTask(Integer userId) {
        String query = "SELECT t FROM Task t WHERE t.author = :userId";
        TypedQuery<Task> request = entityManager.createQuery(query, Task.class);
        request.setParameter("userId", userId);
        List<Task> tasks = request.getResultList();
        return tasks.stream().map(t -> new BriefInformationTaskDto(t.getTitle(), t.getAuthor(), t.getStatus(), t.getPriority())).toList();
    }

    @Override
    public List<BriefInformationTaskDto> getUserExecutingTasks(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserExecutingTasks'");
    }

    @Override
    public List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber, Integer pAGE_SIZE) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsersByUsername'");
    }

    @Override
    public String getAvatarUrl(Integer currentUserId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvatarUrl'");
    }

    @Override
    public Object updateAvatarUrl(String newAvatarUrl, Integer currentUserId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAvatarUrl'");
    }

}
