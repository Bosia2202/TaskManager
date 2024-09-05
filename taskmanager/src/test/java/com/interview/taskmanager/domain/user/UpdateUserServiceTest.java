package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.exception.IncorrectPasswordRuntimeException;
import com.interview.taskmanager.domain.security.IdentificationUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserServiceTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private AvatarStorage avatarStorage;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    private UserUpdateService userUpdateService;

    @BeforeEach
    void init() {
        this.userUpdateService = new UserUpdateService(userGateway, avatarStorage, identificationUserService, passwordEncryptor);
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
