package com.interview.taskmanager.domain;

import lombok.Getter;

@Getter
public class Comment {

    private Integer id;

    private String content;

    private Integer authorId;

    private Integer taskId;

    public Comment(Integer id, String content, Integer authorId, Integer taskId) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
        this.taskId = taskId;
    }

    public Comment(String content, Integer authorId, Integer taskId) {
        this.content = content;
        this.authorId = authorId;
        this.taskId = taskId;
    }

}
