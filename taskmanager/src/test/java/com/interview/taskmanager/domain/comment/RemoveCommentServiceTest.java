package com.interview.taskmanager.domain.comment;

import com.interview.taskmanager.domain.exception.CommentAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.security.AccessRightChecker;
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
public class RemoveCommentServiceTest {

    @Mock
    private CommentGateway commentGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private AccessRightChecker accessRightChecker;

    private RemoveCommentService removeCommentService;

    @BeforeEach
    void init() {
        this.removeCommentService = new RemoveCommentService(commentGateway, identificationUserService, accessRightChecker);
    }

    @Test
    void whenUseMethodRemoveCommentAndUserHasPermissionToComment_thanShouldNotGetCommentAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserComment(anyInt(), anyInt())).thenReturn(true);
        final Integer COMMENT_ID = 1;
        Assertions.assertDoesNotThrow(() -> removeCommentService.remove(COMMENT_ID));
    }

    @Test
    void whenUseMethodRemoveCommentAndUserDoesNotPermissionToComment_thanShouldGetCommentAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserComment(anyInt(), anyInt())).thenReturn(false);
        final Integer COMMENT_ID = 1;
        Assertions.assertThrows(CommentAccessDeniedRuntimeException.class,
                () -> removeCommentService.remove(COMMENT_ID));
    }
}
