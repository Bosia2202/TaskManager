package com.interview.taskmanager.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchUserServiceTest {

    @Mock
    private UserGateway userGateway;

    private SearchUserService searchUserService;

    @BeforeEach
    void init() {
        this.searchUserService = new SearchUserService(userGateway);
    }

    @Test
    void whenUseMethodGetUsersByUsername_thanShouldGetExpectedBriefUserInfo() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_AVATAR_URL = "https://www.test.com/avatar";
        final String EXPECTED_USERNAME = "User";
        List<BriefUserInfo> userGatewayReturnResult = Collections.singletonList(new BriefUserInfo(EXPECTED_USER_ID,
                EXPECTED_AVATAR_URL, EXPECTED_USERNAME));
        final Integer PAGE_NUMBER = 1;
        when(userGateway.getUsersByUsername(anyString(), anyInt()))
                .thenReturn(userGatewayReturnResult);
        List<BriefUserInfo> searchResult = searchUserService.getUsersByUsername(EXPECTED_USERNAME, PAGE_NUMBER);
        BriefUserInfo actualBriefUserInfo = searchResult.get(0);
        Assertions.assertEquals(EXPECTED_USER_ID, actualBriefUserInfo.id());
        Assertions.assertEquals(EXPECTED_AVATAR_URL, actualBriefUserInfo.avatarUrl());
        Assertions.assertEquals(EXPECTED_USERNAME, actualBriefUserInfo.username());
    }
}
