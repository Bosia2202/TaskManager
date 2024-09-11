package com.interview.taskmanager.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interview.taskmanager.application.ports.out.AvatarPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.application.usecase.exception.IncorrectPasswordRuntimeException;
import com.interview.taskmanager.application.usecase.security.IdentificationUserService;
import com.interview.taskmanager.application.usecase.user.PasswordEncryptor;
import com.interview.taskmanager.application.usecase.user.UpdateUserService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserServiceTest {

    @Mock
    private UserPort userGateway;

    @Mock
    private AvatarPort avatarStorage;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    private UpdateUserService userUpdateService;

    @BeforeEach
    void init() {
        this.userUpdateService = new UpdateUserService(userGateway, avatarStorage, identificationUserService, passwordEncryptor);
    }

    @Test
    void whenUseMethodUpdateUsername_thanShouldNotGetRuntimeExceptions() {
        final String NEW_USERNAME = "New user";
        Assertions.assertDoesNotThrow(() -> userUpdateService.updateUsername(NEW_USERNAME));
    }

    @Test
    void whenUseMethodUpdatePassword_thanShouldNotGetRuntimeExceptions() {
        final char[] OLD_PASSWORD = {'1', '2', '3', '4'};
        final char[] NEW_PASSWORD = {'0', '0', '0', '0'};
        when(userGateway.getPassword(anyInt())).thenReturn("password");
        when(passwordEncryptor.encryptPassword(OLD_PASSWORD)).thenReturn("password");
        Assertions.assertDoesNotThrow(() -> userUpdateService.updatePassword(OLD_PASSWORD, NEW_PASSWORD));
    }

    @Test
    void whenUseMethodUpdatePasswordAndPasswordDoesNotCorrect_thanShouldGetIncorrectPasswordRuntimeException() {
        final char[] OLD_PASSWORD = {'1', '2', '3', '4'};
        final char[] NEW_PASSWORD = {'0', '0', '0', '0'};
        when(userGateway.getPassword(anyInt())).thenReturn("password");
        when(passwordEncryptor.encryptPassword(OLD_PASSWORD)).thenReturn("IncorrectPassword");
        Assertions.assertThrows(IncorrectPasswordRuntimeException.class, () -> userUpdateService
                .updatePassword(OLD_PASSWORD, NEW_PASSWORD));
    }
}
