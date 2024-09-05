package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.task.BriefInformationTaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserProfileTest {

    @Mock
    private UserGateway userGateway;

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
        final List<BriefInformationTaskDto> EXPECTED_CUSTOM_TASKS = Collections.emptyList();
        final List<BriefInformationTaskDto> EXPECTED_EXECUTING_TASKS = Collections.emptyList();
        ProfileDto profileDto = new ProfileDto(EXPECTED_USER_ID, EXPECTED_AVATAR_URL, EXPECTED_USERNAME,
                EXPECTED_CUSTOM_TASKS, EXPECTED_EXECUTING_TASKS);
        when(userGateway.getUserProfile(anyInt())).thenReturn(profileDto);
        ProfileDto actualProfileDto = userProfileService.getUserProfile(EXPECTED_USER_ID);
        Assertions.assertEquals(EXPECTED_USER_ID, actualProfileDto.id());
        Assertions.assertEquals(EXPECTED_AVATAR_URL, actualProfileDto.avatarUrl());
        Assertions.assertEquals(EXPECTED_USERNAME, actualProfileDto.username());
        Assertions.assertEquals(EXPECTED_CUSTOM_TASKS, actualProfileDto.customTasks());
        Assertions.assertEquals(EXPECTED_EXECUTING_TASKS, actualProfileDto.executingTasks());
    }
}
