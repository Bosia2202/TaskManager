package com.interview.taskmanager.adapters.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.interview.taskmanager.domain.task.BriefTaskDto;
import com.interview.taskmanager.domain.user.BriefUserInfo;
import com.interview.taskmanager.domain.user.ProfileDto;
import com.interview.taskmanager.domain.user.Role;
import com.interview.taskmanager.domain.user.UserGateway;

@Component
public class UserGatewayAdapter implements UserGateway {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public UserGatewayAdapter(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public boolean create(String email, String defaultAvatarUrl, String username, String password, Role role) {
        return userRepository.create(email, defaultAvatarUrl, username, password, role);
    }

    @Override
    public void updateUsername(String newUsername, Integer userId) {
        userRepository.updateUsername(newUsername, userId);
    }

    @Override
    public String getPassword(Integer userId) {
        return userRepository.getPassword(userId);
    }

    @Override
    public void updatePassword(String encryptedNewPassword, Integer userId) {
        userRepository.updatePassword(encryptedNewPassword, userId);
    }

    @Override
    public boolean remove(Integer userId) {
        return userRepository.remove(userId);
    }

    @Override
    public Optional<ProfileDto> getUserProfile(Integer userId) {
        Optional<DatabaseUserDto> databaseResponse = userRepository.getUserById(userId);
        if (databaseResponse.isEmpty()) {
            return Optional.empty();
        }
        DatabaseUserDto user = databaseResponse.get();
        List<BriefTaskDto> customTasks = taskRepository.getCustomTaskByUserId(userId);
        List<BriefTaskDto> executingTasks = taskRepository.getExecutingTasksByUserId(userId);
        return Optional.of(new ProfileDto(user.id(), user.avatarUrl(), user.username(),
                customTasks, executingTasks));
    }

    @Override
    public List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber) {    //TODO: Добавить pageSize в бизнес логику и в метод.
        final Integer PAGE_SIZE = 20;
        List<DatabaseUserDto> users = userRepository.getUsersByUsername(username, pageNumber, PAGE_SIZE);
        return users.stream().map(u -> new BriefUserInfo(u.id(), u.avatarUrl(), u.username())).toList(); 
    }

    @Override
    public String getUserAvatarUrl(Integer currentUserId) {
        return userRepository.getAvatarUrl(currentUserId);
    }

    @Override
    public void updateUserAvatarUrl(String newAvatarUrl, Integer currentUserId) {
        userRepository.updateAvatarUrl(newAvatarUrl, currentUserId);
    }

}
