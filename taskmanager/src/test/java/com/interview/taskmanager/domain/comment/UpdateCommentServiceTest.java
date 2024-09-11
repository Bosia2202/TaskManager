package com.interview.taskmanager.domain.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interview.taskmanager.application.dao.CommentDao;
import com.interview.taskmanager.application.usecase.comment.UpdateCommentService;
import com.interview.taskmanager.application.usecase.exception.CommentAccessDeniedRuntimeException;
import com.interview.taskmanager.application.usecase.security.AccessRightChecker;
import com.interview.taskmanager.application.usecase.security.IdentificationUserService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCommentServiceTest {

    @Mock
    private CommentDao commentGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    @Mock
    private AccessRightChecker accessRightChecker;

    private UpdateCommentService updateCommentService;

    @BeforeEach
    void init() {
        updateCommentService = new UpdateCommentService(commentGateway, identificationUserService, accessRightChecker);
    }

    @Test
    void whenUseMethodUpdateCommentAndUserHasPermissionToComment_thanShouldNotGetCommentAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserComment(anyInt(), anyInt())).thenReturn(true);
        final String COMMENT_NEW_CONTENT = "New content";
        final Integer COMMENT_ID = 1;
        Assertions.assertDoesNotThrow(() -> updateCommentService.updateContent(COMMENT_NEW_CONTENT, COMMENT_ID));
    }

    @Test
    void whenUseMethodUpdateCommentAndUserDoesNotPermissionToComment_thanShouldGetCommentAccessDeniedRuntimeException() {
        when(accessRightChecker.isUserComment(anyInt(), anyInt())).thenReturn(false);
        final String COMMENT_NEW_CONTENT = "New content";
        final Integer COMMENT_ID = 1;
        Assertions.assertThrows(CommentAccessDeniedRuntimeException.class,
                () -> updateCommentService.updateContent(COMMENT_NEW_CONTENT, COMMENT_ID));
    }

}
