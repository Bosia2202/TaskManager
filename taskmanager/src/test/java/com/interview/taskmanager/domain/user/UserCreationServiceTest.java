package com.interview.taskmanager.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserCreationServiceTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private AvatarStorage avatarStorage;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    private UserCreationService userCreationService;

    @BeforeEach
    void init() {
        this.userCreationService = new UserCreationService(userGateway, avatarStorage, passwordEncryptor);
    }

    @Test
    void whenUseMethodCreateDefaultUser_thanShouldNotGetRuntimeExceptions() {
        final String USER_EMAIL = "test@gmail.com";
        final String USER_USERNAME = "user";
        final char[] USER_PASSWORD = {'1','2','3','4'};
        CreateUserDto createUserDto = new CreateUserDto(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        Assertions.assertDoesNotThrow(() -> userCreationService.createDefaultUser(createUserDto));
    }
}
