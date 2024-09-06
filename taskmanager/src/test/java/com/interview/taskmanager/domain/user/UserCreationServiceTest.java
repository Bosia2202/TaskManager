package com.interview.taskmanager.domain.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interview.taskmanager.domain.exception.UserAlreadyExistRuntimeException;

@ExtendWith(MockitoExtension.class)
class UserCreationServiceTest {

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
        final char[] USER_PASSWORD = { '1', '2', '3', '4' };
        CreateUserDto createUserDto = new CreateUserDto(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        when(avatarStorage.getDefaultAvatarImgUrl()).thenReturn("https://www.avatar.com");
        when(passwordEncryptor.encryptPassword(USER_PASSWORD)).thenReturn("encrypted password");
        when(userGateway.create(anyString(), anyString(), anyString(), anyString(), any()))
                .thenReturn(true);
        Assertions.assertDoesNotThrow(() -> userCreationService.createDefaultUser(createUserDto));
    }

    @Test
    void whenUseMethodCreateDefaultUserAndUserAlreadyExist_thanShouldGetUserAlreadyExistRuntimeException() {
        final String USER_EMAIL = "test@gmail.com";
        final String USER_USERNAME = "user";
        final char[] USER_PASSWORD = { '1', '2', '3', '4' };
        CreateUserDto createUserDto = new CreateUserDto(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        when(avatarStorage.getDefaultAvatarImgUrl()).thenReturn("https://www.avatar.com");
        when(passwordEncryptor.encryptPassword(USER_PASSWORD)).thenReturn("encrypted password");
        when(userGateway.create(anyString(), anyString(), anyString(), anyString(), any()))
                .thenReturn(true);
        when(userGateway.create(anyString(), anyString(), anyString(), anyString(), any())).thenReturn(false);
        Assertions.assertThrows(UserAlreadyExistRuntimeException.class,
                () -> userCreationService.createDefaultUser(createUserDto));
    }
}
