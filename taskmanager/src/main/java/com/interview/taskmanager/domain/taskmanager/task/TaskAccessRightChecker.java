package com.interview.taskmanager.domain.taskmanager.task;

public interface TaskAccessRightChecker {

    boolean isUserTask(Integer taskId, Integer userId);

}
