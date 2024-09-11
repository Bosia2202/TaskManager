package com.interview.taskmanager.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interview.taskmanager.application.dto.UserDto;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.application.usecase.exception.UserProfileNotFoundRuntimeException;
import com.interview.taskmanager.application.usecase.task.TaskPreviewDto;
import com.interview.taskmanager.application.usecase.user.UserProfileService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileTest {

    @Mock
    private UserPort userGateway;

    private UserProfileService userProfileService;

    @BeforeEach
    void init() {
        this.userProfileService = new UserProfileService(userGateway);
    }

    @Test
    void whenUseMethodGetUserProfile_thanShouldGetExpectedProfileDto() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_AVATAR_URL = "https://www.test.com/avatar";
        final String EXPECTED_USERNAME = "User";
        final List<TaskPreviewDto> EXPECTED_CUSTOM_TASKS = Collections.emptyList();
        final List<TaskPreviewDto> EXPECTED_EXECUTING_TASKS = Collections.emptyList();
        UserDto profileDto = new UserDto(EXPECTED_USER_ID, EXPECTED_AVATAR_URL, EXPECTED_USERNAME,
                EXPECTED_CUSTOM_TASKS, EXPECTED_EXECUTING_TASKS);
        when(userGateway.getUserProfile(anyInt())).thenReturn(Optional.of(profileDto));
        UserDto actualProfileDto = userProfileService.getUserProfile(EXPECTED_USER_ID);
        Assertions.assertEquals(EXPECTED_USER_ID, actualProfileDto.id());
        Assertions.assertEquals(EXPECTED_AVATAR_URL, actualProfileDto.avatarUrl());
        Assertions.assertEquals(EXPECTED_USERNAME, actualProfileDto.username());
        Assertions.assertEquals(EXPECTED_CUSTOM_TASKS, actualProfileDto.customTasks());
        Assertions.assertEquals(EXPECTED_EXECUTING_TASKS, actualProfileDto.executingTasks());
    }

    @Test
    void whenUseMethodGetUserProfileAndUserDoesNotExist_thanShouldGetUserProfileNotFoundRuntimeException() {
        final Integer USER_ID = 1;
        when(userGateway.getUserProfile(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundRuntimeException.class,
                () -> userProfileService.getUserProfile(USER_ID));
    }
}
