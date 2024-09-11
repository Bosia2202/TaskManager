package com.interview.taskmanager.application.usecase.search;

import java.util.List;

import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.application.dto.UserPreviewDto;
import com.interview.taskmanager.application.dto.UserDto;
import com.interview.taskmanager.application.ports.out.TaskPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.application.usecase.task.TaskPreviewDto;
import com.interview.taskmanager.infra.exception.UserNotFoundRuntimeException;

public class SearchUserService {

        private final UserPort userPort;

        private final TaskPort taskPort;

        public SearchUserService(UserPort userPort, TaskPort taskPort) {
                this.userPort = userPort;
                this.taskPort = taskPort;
        }

        public UserDto getUser(Integer userId) {
                DatabaseUserDto databaseUser = userPort.getUserById(userId)
                                .orElseThrow(() -> buildUserNotFoundRuntimeException(userId));
                List<TaskPreviewDto> customTasks = taskPort.getCustomTasksByUserId(userId).stream()
                                .map(task -> new TaskPreviewDto(task.title(), task.status().toString(),
                                                task.priority().toString(), task.authorId(),
                                                databaseUser.username()))
                                .toList();
                List<TaskPreviewDto> executedTasks = taskPort.getExecutedTasksByUserId(userId).stream()
                                .map(task -> new TaskPreviewDto(task.title(), task.status().toString(),
                                                task.priority().toString(), task.authorId(),
                                                userPort.getUsernameById(task.authorId())))
                                .toList();
                return new UserDto(userId, databaseUser.avatarUrl(), databaseUser.username(), customTasks,
                                executedTasks);
        } //TODO:Подумать как решить проблемму  'userPort.getUsernameById(task.authorId()'

        private UserNotFoundRuntimeException buildUserNotFoundRuntimeException(Integer userId) {
                String message = String.format("User profile with id = '%d' wasn't found", userId);
                return new UserNotFoundRuntimeException(message);
        }

        public List<UserPreviewDto> getUsersByUsername(String username, Integer pageNumber) {
                List<DatabaseUserDto> users = userPort.getUsersByUsername(username, pageNumber);
                return users.stream().map(user -> new UserPreviewDto(user.id(), user.avatarUrl(), user.username()))
                                .toList();
        }

}
