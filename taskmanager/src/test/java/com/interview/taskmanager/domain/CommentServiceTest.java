package com.interview.taskmanager.domain;

import com.interview.taskmanager.domain.exception.CommentAccessDeniedRuntimeException;
import com.interview.taskmanager.domain.taskmanager.comment.CommentGateway;
import com.interview.taskmanager.domain.taskmanager.comment.CommentRightChecker;
import com.interview.taskmanager.domain.taskmanager.comment.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentGateway commentGateway;

    @Mock
    private CommentRightChecker commentRightChecker;

    private CommentService commentService;

    @BeforeEach
    void init() {
        commentService = new CommentService(commentGateway, commentRightChecker);
    }

    @Test
    void whenUseMethodUpdateCommentAndUserDoesNotPermissionToComment_thanShouldGetCommentAccessDeniedRuntimeException() {
        when(commentRightChecker.isUserComment(anyInt(), anyInt())).thenReturn(false);
        final String COMMENT_NEW_CONTENT = "New content";
        final Integer COMMENT_ID = 1;
        final Integer CURRENT_USER_ID = 1;
        Assertions.assertThrows(CommentAccessDeniedRuntimeException.class,
                () -> commentService.updateCommentContent(COMMENT_NEW_CONTENT, COMMENT_ID, CURRENT_USER_ID));
    }

    @Test
    void whenUseMethodRemoveCommentAndUserDoesNotPermissionToComment_thanShouldGetCommentAccessDeniedRuntimeException() {
        when(commentRightChecker.isUserComment(anyInt(), anyInt())).thenReturn(false);
        final Integer COMMENT_ID = 1;
        final Integer CURRENT_USER_ID = 1;
        Assertions.assertThrows(CommentAccessDeniedRuntimeException.class,
                () -> commentService.removeComment(COMMENT_ID, CURRENT_USER_ID));
    }

}
