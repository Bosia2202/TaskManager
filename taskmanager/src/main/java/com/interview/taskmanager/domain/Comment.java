package com.interview.taskmanager.domain;

import lombok.Data;

@Data
public class Comment {

    private Integer id;

    private String content;

    private Integer authorId;

    private String username;

    private Integer taskId;

}
