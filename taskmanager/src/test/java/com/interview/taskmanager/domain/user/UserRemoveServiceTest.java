package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.security.IdentificationUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRemoveServiceTest {

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
        Assertions.assertDoesNotThrow(() -> userRemoveService.remove());
    }
}
