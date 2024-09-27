package com.interview.taskmanager.adapters.out.postgresql.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;

import com.interview.taskmanager.adapters.out.postgresql.comment.repository.CommentRepository;
import com.interview.taskmanager.application.dto.DatabaseCommentDto;
import com.interview.taskmanager.application.dto.DatabaseCreateSubCommentDto;
import com.interview.taskmanager.application.dto.DatabaseSubCommentDto;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @TestConfiguration
    static class Config {

        @Bean
        public CommentRepository commentRepository() {
            return new CommentRepository();
        }
    }

    @Test
    void whenUseMethodSave_thanShouldGetExpectedComment() {
        final String EXPECTED_CONTENT = "Expected content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        final Integer EXPECTED_TASK_ID = 1;
        DatabaseCommentDto databaseCommentDto = new DatabaseCommentDto(1, EXPECTED_CONTENT, EXPECTED_AUTHOR_ID, EXPECTED_TASK_ID);
        commentRepository.save(databaseCommentDto);
        DatabaseCommentDto actualComment = commentRepository.getCommentById(1);
        Assertions.assertEquals(EXPECTED_CONTENT, actualComment.content());
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualComment.authorId());
        Assertions.assertEquals(EXPECTED_TASK_ID, actualComment.taskId());
    }

    @Test
    @Sql("CreateComment.sql")
    void whenUseMethodSaveSubComment_thanShouldGetExpectedSubComment() {
        final Integer EXPECTED_COMMENT_ID = 1;
        final String EXPECTED_CONTENT = "Expected content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        final Integer SUB_COMMENT_ID = 1;
        DatabaseCreateSubCommentDto subCommentDto = new DatabaseCreateSubCommentDto(EXPECTED_CONTENT, EXPECTED_AUTHOR_ID, EXPECTED_COMMENT_ID);
        commentRepository.saveSubComment(subCommentDto);
        DatabaseSubCommentDto actualSubComment = commentRepository.getSubCommentById(SUB_COMMENT_ID);
        Assertions.assertEquals(EXPECTED_CONTENT, actualSubComment.content());
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualSubComment.authorId());
        Assertions.assertEquals(EXPECTED_COMMENT_ID, actualSubComment.commentId());
    }


}
