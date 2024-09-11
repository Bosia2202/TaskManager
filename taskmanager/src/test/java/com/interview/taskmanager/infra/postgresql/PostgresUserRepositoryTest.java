package com.interview.taskmanager.infra.postgresql;

import com.interview.taskmanager.adapters.database.in.springsecurity.Role;
import com.interview.taskmanager.application.dto.DatabaseUserDto;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PostgresUserRepositoryTest {

    @Autowired
    private PostgresUserRepository postgresUserRepository;

    @TestConfiguration
    static class Config {

        @Bean
        public PostgresUserRepository postgresUserRepository() {
            return new PostgresUserRepository();
        }
    }

    @Test
    void whenUseMethodCreate_thanShouldGetTrue() {
        final String USER_EMAIL = "test@yandex.ru";
        final String USER_AVATAR_URL = "https://www.avatar.com";
        final String USER_USERNAME = "User";
        final String USER_PASSWORD = "password";
        final Role USER_ROLE = Role.USER;
        Assertions.assertTrue(() -> postgresUserRepository.create(USER_EMAIL, USER_AVATAR_URL, USER_USERNAME,
                USER_PASSWORD, USER_ROLE));
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodCreateAndUserAlreadyExist_thanShouldGetFalse() {
        final String USER_EMAIL = "test@yandex.ru";
        final String USER_AVATAR_URL = "https://www.avatar.com";
        final String USER_USERNAME = "User";
        final String USER_PASSWORD = "password";
        final Role USER_ROLE = Role.USER;
        Assertions.assertFalse(() -> postgresUserRepository.create(USER_EMAIL, USER_AVATAR_URL, USER_USERNAME,
                USER_PASSWORD, USER_ROLE));
    }


    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodGetUserById_thanShouldGetExpectedUser() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_USER_EMAIL = "test@yandex.ru";
        final String EXPECTED_USER_AVATAR_URL = "https://www.avatar.com";
        final String EXPECTED_USER_USERNAME = "User";
        final String EXPECTED_USER_PASSWORD = "password";
        final Role EXPECTED_USER_ROLE = Role.USER;
        DatabaseUserDto actualUser = postgresUserRepository.getUserById(EXPECTED_USER_ID).get();
        Assertions.assertEquals(EXPECTED_USER_ID, actualUser.id());
        Assertions.assertEquals(EXPECTED_USER_EMAIL, actualUser.email());
        Assertions.assertEquals(EXPECTED_USER_AVATAR_URL, actualUser.avatarUrl());
        Assertions.assertEquals(EXPECTED_USER_USERNAME, actualUser.username());
        Assertions.assertEquals(EXPECTED_USER_PASSWORD, actualUser.password());
        Assertions.assertEquals(EXPECTED_USER_ROLE, actualUser.role());
    }

    @Test
    void whenUseMethodGetUserByIdAndUserDoesNotExist_thanShouldGetEmptyOptional() {
        final Integer EXPECTED_USER_ID = 1;
        Assertions.assertTrue(postgresUserRepository.getUserById(EXPECTED_USER_ID).isEmpty());
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodGetUsersByUsername_thanShouldGetExpectedUser() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_USER_EMAIL = "test@yandex.ru";
        final String EXPECTED_USER_AVATAR_URL = "https://www.avatar.com";
        final String EXPECTED_USER_USERNAME = "User";
        final String EXPECTED_USER_PASSWORD = "password";
        final Role EXPECTED_USER_ROLE = Role.USER;
        final Integer PAGE_NUMBER = 1;
        final Integer PAGE_SIZE = 10;
        List<DatabaseUserDto> users = postgresUserRepository.getUsersByUsername(EXPECTED_USER_USERNAME, PAGE_NUMBER,
                PAGE_SIZE);
        DatabaseUserDto actualUser = users.get(0);
        Assertions.assertEquals(EXPECTED_USER_ID, actualUser.id());
        Assertions.assertEquals(EXPECTED_USER_EMAIL, actualUser.email());
        Assertions.assertEquals(EXPECTED_USER_AVATAR_URL, actualUser.avatarUrl());
        Assertions.assertEquals(EXPECTED_USER_USERNAME, actualUser.username());
        Assertions.assertEquals(EXPECTED_USER_PASSWORD, actualUser.password());
        Assertions.assertEquals(EXPECTED_USER_ROLE, actualUser.role());
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodGetAvatarUrl_thanShouldGetExpectedAvatarUrl() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_USER_AVATAR_URL = "https://www.avatar.com";
        String actualAvatarUrl = postgresUserRepository.getAvatarUrl(EXPECTED_USER_ID);
        Assertions.assertEquals(EXPECTED_USER_AVATAR_URL, actualAvatarUrl);
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodGetPassword_thanShouldGetExpectedPassword() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_USER_PASSWORD = "password";
        String actualPassword = postgresUserRepository.getPassword(EXPECTED_USER_ID);
        Assertions.assertEquals(EXPECTED_USER_PASSWORD, actualPassword);
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodUpdateAvatarUrl_thanShouldGetExpectedNewAvatarUrl() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_USER_NEW_AVATAR_URL = "https://www.avatar.com/new";
        postgresUserRepository.updateAvatarUrl(EXPECTED_USER_NEW_AVATAR_URL, EXPECTED_USER_ID);
        String actualAvatarUrl = postgresUserRepository.getAvatarUrl(EXPECTED_USER_ID);
        Assertions.assertEquals(EXPECTED_USER_NEW_AVATAR_URL, actualAvatarUrl);
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodUpdateUsername_thanShouldGetExpectedNewUsername() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_USER_NEW_USERNAME = "NEW USER NAME";
        postgresUserRepository.updateUsername(EXPECTED_USER_NEW_USERNAME, EXPECTED_USER_ID);
        DatabaseUserDto user = postgresUserRepository.getUserById(EXPECTED_USER_ID).get();
        Assertions.assertEquals(EXPECTED_USER_NEW_USERNAME, user.username());
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodUpdatePassword_thanShouldGetExpectedNewPassword() {
        final Integer EXPECTED_USER_ID = 1;
        final String EXPECTED_USER_NEW_PASSWORD = "NEW_PASSWORD";
        postgresUserRepository.updatePassword(EXPECTED_USER_NEW_PASSWORD, EXPECTED_USER_ID);
        String actualPassword = postgresUserRepository.getPassword(EXPECTED_USER_ID);
        Assertions.assertEquals(EXPECTED_USER_NEW_PASSWORD, actualPassword);
    }

    @Sql("CreateTestUser.sql")
    @Test
    void whenUseMethodRemove_thanShouldGetTrue() {
        final Integer USER_ID = 1;
        Assertions.assertTrue(postgresUserRepository.remove(USER_ID));
    }

    @Test
    void whenUseMethodRemoveAndUserDoesNotExist_thanShouldGetFalse() {
        final Integer USER_ID = 1;
        Assertions.assertFalse(postgresUserRepository.remove(USER_ID));
    }
}
