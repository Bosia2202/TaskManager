package com.interview.taskmanager.adapters.database;

import java.util.List;

import com.interview.taskmanager.domain.task.BriefInformationTaskDto;
import com.interview.taskmanager.domain.user.BriefUserInfo;
import com.interview.taskmanager.domain.user.Role;

public interface UserRepository {

    void create(String email, String defaultAvatarUrl, String username, String password, Role role);

    void updateUsername(String newUsername, Integer userId);

    String getPassword(Integer userId);

    void updatePassword(String encryptedNewPassword, Integer userId);

    void remove(Integer userId);

    SimpleProfileInfo getSimpleProfileInfo(Integer userId);

    List<BriefInformationTaskDto> getUserCustomTask(Integer userId);

    List<BriefInformationTaskDto> getUserExecutingTasks(Integer userId);

    List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber, Integer pAGE_SIZE);

    String getAvatarUrl(Integer currentUserId);

    Object updateAvatarUrl(String newAvatarUrl, Integer currentUserId);
}
