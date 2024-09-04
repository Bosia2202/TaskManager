package com.interview.taskmanager.domain.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentRetrievalServiceTest {

    @Mock
    private CommentGateway commentGateway;

    private CommentRetrievalService commentRetrievalService;

    @BeforeEach
    void init() {
        this.commentRetrievalService = new CommentRetrievalService(commentGateway);
    }

    @Test
    void whenUseMethodGetComments_thanShouldGetExpectedComment() {
        final String EXPECTED_COMMENT_CONTENT = "Content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        final Integer TASK_ID = 1;
        final Integer PAGE_NUMBER = 1;
        CommentDto commentDto = new CommentDto(EXPECTED_COMMENT_CONTENT, EXPECTED_AUTHOR_ID);
        when(commentGateway.getComments(anyInt(), anyInt(), anyInt())).thenReturn(Collections.singletonList(commentDto));
        List<CommentDto> comments = commentRetrievalService.getComments(TASK_ID, PAGE_NUMBER);
        CommentDto actualComment = comments.get(0);
        Assertions.assertEquals(EXPECTED_COMMENT_CONTENT, actualComment.content());
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualComment.authorId());
    }

    @Test
    void whenUseMethodGetSubComments_thanShouldGetExpectedSubComment() {
        final String EXPECTED_SUB_COMMENT_CONTENT = "Content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        final Integer COMMENT_ID = 1;
        final Integer PAGE_NUMBER = 1;
        CommentDto commentDto = new CommentDto(EXPECTED_SUB_COMMENT_CONTENT, EXPECTED_AUTHOR_ID);
        when(commentGateway.getSubComments(anyInt(), anyInt(), anyInt())).thenReturn(Collections.singletonList(commentDto));
        List<CommentDto> comments = commentRetrievalService.getSubComments(COMMENT_ID, PAGE_NUMBER);
        CommentDto actualComment = comments.get(0);
        Assertions.assertEquals(EXPECTED_SUB_COMMENT_CONTENT, actualComment.content());
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualComment.authorId());
    }
}
