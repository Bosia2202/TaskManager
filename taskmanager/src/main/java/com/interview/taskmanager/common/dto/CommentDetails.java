package com.interview.taskmanager.common.dto;

import com.interview.taskmanager.adapters.database.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentDetails {
    private String content;
    private User author;
}
