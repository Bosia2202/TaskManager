package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.exception.UserNotDeletedRuntimeException;
import com.interview.taskmanager.domain.security.IdentificationUserService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRemoveServiceTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    private UserRemoveService userRemoveService;

    @BeforeEach
    void init() {
        this.userRemoveService = new UserRemoveService(userGateway, identificationUserService);
    }

    @Test
    void whenUseMethodRemove_thanShouldNotGetRuntimeExceptions() {
        when(userGateway.remove(anyInt())).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> userRemoveService.remove());
    }

    @Test
    void whenUseMethodRemoveAndUserDoesNotExist_thanShouldGetUserDoesNotDeleteRuntimeException() {
        when(userGateway.remove(anyInt())).thenReturn(false);
        Assertions.assertThrows(UserNotDeletedRuntimeException.class, () -> userRemoveService.remove());
    }

}
