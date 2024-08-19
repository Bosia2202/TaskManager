package com.interview.taskmanager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import com.interview.taskmanager.adapters.database.CommentRepositoryAdapter;
import com.interview.taskmanager.adapters.database.TaskRepositoryAdapter;
import com.interview.taskmanager.adapters.database.UserRepositoryAdapter;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.domain.services.task.TaskManagementService;
import com.interview.taskmanager.domain.services.task.TaskOperation;

@SpringBootTest
@ActiveProfiles("test")
class TaskManagementServiceTest {

    @Mock
    private CommentRepositoryAdapter commentRepositoryAdapter;

    @Mock
    private TaskRepositoryAdapter taskRepositoryAdapter;

    @Mock
    private UserRepositoryAdapter userRepositoryAdapter;

    private TaskManagementService taskManagementService;

    @BeforeEach
    void init() {
        this.taskManagementService = new TaskOperation(userRepositoryAdapter, taskRepositoryAdapter,
                commentRepositoryAdapter);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn((Principal) new Principal() {

            @Override
            public String getName() {
                return "testUser";
            }
            
        });
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

}
