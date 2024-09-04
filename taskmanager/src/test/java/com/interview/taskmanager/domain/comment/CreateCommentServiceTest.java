package com.interview.taskmanager.domain.comment;

import com.interview.taskmanager.domain.security.IdentificationUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateCommentServiceTest {

    @Mock
    private CommentGateway commentGateway;

    @Mock
    private IdentificationUserService identificationUserService;

    private CreateCommentService createCommentService;

    @BeforeEach
    void init() {
        this.createCommentService = new CreateCommentService(commentGateway, identificationUserService);
    }

    @Test
    void whenUseMethodCreateComment_thanShouldNotGetRuntimeException() {
        final String COMMENT_CONTENT = "content";
        final Integer TASK_ID = 1;
        Assertions.assertDoesNotThrow(() -> createCommentService.createComment(COMMENT_CONTENT, TASK_ID));
    }

    @Test
    void whenUseMethodCreateSubComment_thanShouldNotGetRuntimeException() {
        final String SUB_COMMENT_CONTENT = "content";
        final Integer COMMENT_ID = 1;
        Assertions.assertDoesNotThrow(() -> createCommentService.createSubComment(SUB_COMMENT_CONTENT, COMMENT_ID));
    }
}
