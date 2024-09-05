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

    private UserRepository userRepository;

    @Autowired
    public UserGatewayAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        SimpleProfileInfo simpleProfileInfo = userRepository.getSimpleProfileInfo(userId);
        List<BriefInformationTaskDto> customTasks = userRepository.getUserCustomTask(userId);
        List<BriefInformationTaskDto> executingTasks = userRepository.getUserExecutingTasks(userId);
        return new ProfileDto(simpleProfileInfo.id(), simpleProfileInfo.avatarUrl(), simpleProfileInfo.username(),
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
