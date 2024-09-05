package com.interview.taskmanager.adapters.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.interview.taskmanager.domain.task.BriefInformationTaskDto;
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
    public void create(String email, String defaultAvatarUrl, String username, String password, Role role) {
        userRepository.create(email, defaultAvatarUrl, username, password, role);
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
    public void remove(Integer userId) {
        userRepository.remove(userId);
    }

    @Override
    public ProfileDto getUserProfile(Integer userId) {
        DatabaseUserDto databaseUserDto = userRepository.getUserById(userId);
        List<BriefInformationTaskDto> customTasks = taskRepository.getCustomTaskByUserId(userId);
        List<BriefInformationTaskDto> executingTasks = taskRepository.getExecutingTasksByUserId(userId);
        return new ProfileDto(databaseUserDto.id(), databaseUserDto.avatarUrl(), databaseUserDto.username(),
                customTasks, executingTasks);
    }

    @Override
    public List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return userRepository.getUsersByUsername(username, pageNumber, PAGE_SIZE);
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
