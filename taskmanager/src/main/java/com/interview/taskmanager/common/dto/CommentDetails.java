package com.interview.taskmanager.common.dto;

import com.interview.taskmanager.adapters.database.models.Task;
import com.interview.taskmanager.adapters.database.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDetails {

    private String content;
    private User author;
    private Task task;

}
