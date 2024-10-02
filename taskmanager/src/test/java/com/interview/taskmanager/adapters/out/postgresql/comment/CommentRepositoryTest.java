package com.interview.taskmanager.adapters.out.postgresql.comment;

import java.util.List;

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
import com.interview.taskmanager.infra.exception.SubCommentCreateRuntimeException;
import com.interview.taskmanager.infra.exception.SubCommentUpdateRuntimeException;
import com.interview.taskmanager.infra.exception.CommentUpdateRuntimeException;

import jakarta.persistence.NoResultException;

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
        DatabaseCommentDto databaseCommentDto = new DatabaseCommentDto(1, EXPECTED_CONTENT, EXPECTED_AUTHOR_ID,
                EXPECTED_TASK_ID);
        Assertions.assertDoesNotThrow(() -> commentRepository.save(databaseCommentDto));
    }

    @Test
    @Sql("CreateComment.sql")
    void whenUseMethodSaveSubComment_thanShouldGetExpectedSubComment() {
        final Integer EXPECTED_COMMENT_ID = 1;
        final String EXPECTED_CONTENT = "Expected content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        DatabaseCreateSubCommentDto subCommentDto = new DatabaseCreateSubCommentDto(EXPECTED_CONTENT,
                EXPECTED_AUTHOR_ID, EXPECTED_COMMENT_ID);
        Assertions.assertDoesNotThrow(() -> commentRepository.saveSubComment(subCommentDto));
    }

    @Test
    void whenUseMethodSaveSubCommentAndPrimaryCommentDoesntExist_thanShouldGetCreateSubCommentRuntimeException() {
        final Integer EXPECTED_COMMENT_ID = 1;
        final String EXPECTED_CONTENT = "Expected content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        DatabaseCreateSubCommentDto subCommentDto = new DatabaseCreateSubCommentDto(EXPECTED_CONTENT,
                EXPECTED_AUTHOR_ID, EXPECTED_COMMENT_ID);
        Assertions.assertThrows(SubCommentCreateRuntimeException.class,
                () -> commentRepository.saveSubComment(subCommentDto));
    }

    @Test
    @Sql("CreateComment.sql")
    void whenUseMethodGetCommentById_thanShouldGetExpectedComment() {
        final Integer EXPECTED_COMMENT_ID = 1;
        final String EXPECTED_CONTENT = "Content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        final Integer EXPECTED_TASK_ID = 1;
        DatabaseCommentDto actualComment = commentRepository.getCommentById(EXPECTED_COMMENT_ID);
        Assertions.assertEquals(EXPECTED_CONTENT, actualComment.content());
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualComment.authorId());
        Assertions.assertEquals(EXPECTED_TASK_ID, actualComment.taskId());
    }

    @Test
    void whenUseMethodGetCommentByIdAndCommentDoesntExist_thanShouldGetNoResultException() {
        final Integer WRONG_COMMENT_ID = 1;
        Assertions.assertThrows(NoResultException.class, () -> commentRepository
                .getCommentById(WRONG_COMMENT_ID));
    }

    @Test
    @Sql("CreateComment.sql")
    @Sql("CreateSubComment.sql")
    void whenUseMethodGetSubCommentById_thanShouldGetExpectedSubComment() {
        final Integer EXPECTED_SUB_COMMENT_ID = 1;
        final String EXPECTED_CONTENT = "Content";
        final Integer EXPECTED_AUTHOR_ID = 1;
        final Integer EXPECTED_COMMENT_ID = 1;
        DatabaseSubCommentDto actualSubComment = commentRepository.getSubCommentById(EXPECTED_SUB_COMMENT_ID);
        Assertions.assertEquals(EXPECTED_CONTENT, actualSubComment.content());
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualSubComment.authorId());
        Assertions.assertEquals(EXPECTED_COMMENT_ID, actualSubComment.commentId());
    }

    @Test
    void whenUseMethodGetSubCommentByIdAndSubCommentDoesntExist_thanShouldGetNoResultException() {
        final Integer WRONG_COMMENT_ID = 2;
        Assertions.assertThrows(NoResultException.class, () -> commentRepository.getSubCommentById(WRONG_COMMENT_ID));
    }

    @Test
    @Sql("CreateComments.sql")
    void whenUseMethodGetCommentsByTaskId_thanShouldGetExpectedComments() {
        final List<DatabaseCommentDto> EXPECTED_COMMENTS = generateExpectedComments();
        final Integer TASK_ID = 1;
        final Integer PAGE_NUMBER = 1;
        final Integer PAGE_SIZE = 10;
        List<DatabaseCommentDto> actualDatabaseComments = commentRepository.getCommentsByTaskId(TASK_ID, PAGE_NUMBER,
                PAGE_SIZE);
        Assertions.assertEquals(EXPECTED_COMMENTS, actualDatabaseComments);
    }

    private List<DatabaseCommentDto> generateExpectedComments() {
        DatabaseCommentDto firstComment = new DatabaseCommentDto(1, "Content", 1, 1);
        DatabaseCommentDto secondComment = new DatabaseCommentDto(2, "Content2", 2, 1);
        return List.of(firstComment, secondComment);
    }

    @Test
    void whenUseMethodGetCommentsByTaskIdAndTaskCommentsDoesntExist_thanShouldGetEmptyList() {
        final Integer TASK_ID = 1;
        final Integer PAGE_NUMBER = 1;
        final Integer PAGE_SIZE = 10;
        Assertions.assertTrue(commentRepository.getCommentsByTaskId(TASK_ID, PAGE_NUMBER, PAGE_SIZE).isEmpty());
    }

    @Test
    @Sql("CreateComment.sql")
    @Sql("CreateSubComments.sql")
    void whenUseMethodGetSubCommentsByCommentId_thanShouldGetExpectedSubComments() {
        final List<DatabaseSubCommentDto> EXPECTED_SUB_COMMENTS = generateExpectedSubComments();
        final Integer COMMENT_ID = 1;
        final Integer PAGE_NUMBER = 1;
        final Integer PAGE_SIZE = 10;
        Assertions.assertEquals(EXPECTED_SUB_COMMENTS,
                commentRepository.getSubCommentsByCommentId(COMMENT_ID, PAGE_NUMBER, PAGE_SIZE));
    }

    private List<DatabaseSubCommentDto> generateExpectedSubComments() {
        DatabaseSubCommentDto firstSubComment = new DatabaseSubCommentDto(1, "Content", 1, 1);
        DatabaseSubCommentDto secondSubComment = new DatabaseSubCommentDto(2, "Content2", 2, 1);
        return List.of(firstSubComment, secondSubComment);
    }

    @Test
    void whenUseMethodGetSubCommentsByCommentIdAndSubCommentsDoesntExist_thanShouldGetEmptyList() {
        final Integer COMMENT_ID = 1;
        final Integer PAGE_NUMBER = 1;
        final Integer PAGE_SIZE = 10;
        Assertions
                .assertTrue(commentRepository.getSubCommentsByCommentId(COMMENT_ID, PAGE_NUMBER, PAGE_SIZE).isEmpty());
    }

    @Test
    @Sql("CreateComment.sql")
    void whenUseMethodGetAuthorIdByCommentId_thanShouldGetExpectedAuthorId() {
        final Integer COMMENT_ID = 1;
        final Integer EXPECTED_AUTHOR_ID = 1;
        Integer actualAuthorId = commentRepository.getAuthorIdByCommentId(COMMENT_ID);
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualAuthorId);
    }

    @Test
    void whenUseMethodGetAuthorIdByCommentIdAndCommentDoesntExist_thanShouldGetNoResultException() {
        final Integer COMMENT_ID = 1;
        Assertions.assertThrows(NoResultException.class, () -> commentRepository.getAuthorIdByCommentId(COMMENT_ID));
    }

    @Test
    @Sql("CreateComment.sql")
    @Sql("CreateSubComment.sql")
    void whenUseMethodGetAuthorIdBySubCommentId_thanShouldGetExpectedAuthorId() {
        final Integer SUB_COMMENT_ID = 1;
        final Integer EXPECTED_AUTHOR_ID = 1;
        Integer actualAuthorId = commentRepository.getAuthorIdByCommentId(SUB_COMMENT_ID);
        Assertions.assertEquals(EXPECTED_AUTHOR_ID, actualAuthorId);
    }

    @Test
    void whenUseMethodGetAuthorIdBySubCommentIdAndSubCommentDoesntExist_thanShouldGetNoResultException() {
        final Integer SUB_COMMENT_ID = 1;
        Assertions.assertThrows(NoResultException.class,
                () -> commentRepository.getAuthorIdByCommentId(SUB_COMMENT_ID));
    }

    @Test
    @Sql("CreateComment.sql")
    void whenUseMethodUpdateComment_thanShouldGetExpectedUpdateComment() {
        final Integer COMMENT_ID = 1;
        final String EXPECTED_COMMENT_CONTENT = "Update content";
        final Integer AUTHOR_ID = 1;
        final Integer TASK_ID = 1;
        DatabaseCommentDto updatedComment = new DatabaseCommentDto(COMMENT_ID, EXPECTED_COMMENT_CONTENT, AUTHOR_ID,
                TASK_ID);
        commentRepository.updateComment(updatedComment);
        DatabaseCommentDto actualComment = commentRepository.getCommentById(COMMENT_ID);
        Assertions.assertEquals(EXPECTED_COMMENT_CONTENT, actualComment.content());
    }

    @Test
    void whenUseMethodUpdateCommentAndCommentDoesntExist_thanShouldGetUpdateCommentRuntimeException() {
        final Integer COMMENT_ID = 1;
        final String EXPECTED_COMMENT_CONTENT = "Update content";
        final Integer AUTHOR_ID = 1;
        final Integer TASK_ID = 1;
        DatabaseCommentDto updatedComment = new DatabaseCommentDto(COMMENT_ID, EXPECTED_COMMENT_CONTENT, AUTHOR_ID,
                TASK_ID);
        Assertions.assertThrows(CommentUpdateRuntimeException.class,
                () -> commentRepository.updateComment(updatedComment));
    }

    @Test
    @Sql("CreateComment.sql")
    @Sql("CreateSubComment.sql")
    void whenUseMethodUpdateSubComment_thanShouldGetExpectedUpdateSubComment() {
        final Integer SUB_COMMENT_ID = 1;
        final String EXPECTED_SUB_COMMENT_CONTENT = "Update content";
        final Integer AUTHOR_ID = 1;
        final Integer COMMENT_ID = 1;
        DatabaseSubCommentDto databaseSubCommentDto = new DatabaseSubCommentDto(SUB_COMMENT_ID,
                EXPECTED_SUB_COMMENT_CONTENT, AUTHOR_ID, COMMENT_ID);
        commentRepository.updateSubComment(databaseSubCommentDto);
        DatabaseSubCommentDto actualSubCommentDto = commentRepository.getSubCommentById(SUB_COMMENT_ID);
        Assertions.assertEquals(EXPECTED_SUB_COMMENT_CONTENT, actualSubCommentDto.content());
    }

    @Test
    void whenUseMethodUpdateSubCommentAndSubCommentDoesntExist_thanShouldGetSubCommentUpdateRuntimeException() {
        final Integer SUB_COMMENT_ID = 1;
        final String EXPECTED_SUB_COMMENT_CONTENT = "Update content";
        final Integer AUTHOR_ID = 1;
        final Integer COMMENT_ID = 1;
        DatabaseSubCommentDto databaseSubCommentDto = new DatabaseSubCommentDto(SUB_COMMENT_ID,
                EXPECTED_SUB_COMMENT_CONTENT, AUTHOR_ID, COMMENT_ID);
        Assertions.assertThrows(SubCommentUpdateRuntimeException.class,
                () -> commentRepository.updateSubComment(databaseSubCommentDto));
    }

    @Test
    @Sql("CreateComment.sql")
    void whenUseMethodRemoveCommentById_thanShouldGetNoResultExceptionUseMethodGetCommentById() {
        final Integer COMMENT_ID = 1;
        commentRepository.removeCommentById(COMMENT_ID);
        Assertions.assertThrows(NoResultException.class, () -> commentRepository.getCommentById(COMMENT_ID));
    }

    @Test
    void whenUseMethodRemoveCommentByIdAndCommentDoesntExist_thanShouldGetNoResultException() {
        final Integer COMMENT_ID = 1;
        Assertions.assertThrows(NoResultException.class, () -> commentRepository.removeCommentById(COMMENT_ID));
    }

    @Test
    @Sql("CreateComment.sql")
    @Sql("CreateSubComment.sql")
    void whenUseMethodRemoveSubCommentById_thanShouldGetNoResultExceptionUseMethodGetCommentById() {
        final Integer SUB_COMMENT_ID = 1;
        commentRepository.removeSubCommentById(SUB_COMMENT_ID);
        Assertions.assertThrows(NoResultException.class, () -> commentRepository.getSubCommentById(SUB_COMMENT_ID));
    }

    @Test
    void whenUseMethodRemoveSubCommentByIdAndSubCommentDoesntExist_thanShouldGetNoResultException() {
        final Integer SUB_COMMENT_ID = 1;
        Assertions.assertThrows(NoResultException.class, () -> commentRepository.removeSubCommentById(SUB_COMMENT_ID));
    }
}
