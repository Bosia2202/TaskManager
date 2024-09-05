package com.interview.taskmanager.infra.postgresql;

import com.interview.taskmanager.adapters.database.DatabaseUserDto;
import com.interview.taskmanager.domain.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PostgresUserRepositoryTest {

    private PostgresUserRepository postgresUserRepository;

    @BeforeEach
    void init() {
        this.postgresUserRepository = new PostgresUserRepository();
    }

    @Test
    void whenUseMethodCreate_thanShouldGetExpectedUser() {
        final String EXPECTED_USER_EMAIL = "test@yandex.ru";
        final String EXPECTED_USER_AVATAR_URL = "https://www.avatar.com";
        final String EXPECTED_USER_USERNAME = "User";
        final String EXPECTED_USER_PASSWORD = "password";
        final Role EXPECTED_USER_ROLE = Role.USER;
        postgresUserRepository.create(EXPECTED_USER_EMAIL, EXPECTED_USER_AVATAR_URL, EXPECTED_USER_USERNAME,
                EXPECTED_USER_PASSWORD, EXPECTED_USER_ROLE);
        DatabaseUserDto actualUser = postgresUserRepository.getUserById(1);
        Assertions.assertEquals(EXPECTED_USER_EMAIL, actualUser.email());
        Assertions.assertEquals(EXPECTED_USER_AVATAR_URL, actualUser.avatarUrl());
        Assertions.assertEquals(EXPECTED_USER_USERNAME, actualUser.username());
        Assertions.assertEquals(EXPECTED_USER_PASSWORD, actualUser.password());
        Assertions.assertEquals(EXPECTED_USER_ROLE, actualUser.role());
    }

}
