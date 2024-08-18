package com.interview.taskmanager.common.dto.comment;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.task.AuthorDto;

import lombok.Getter;

@Getter
public class CommentDto {
    private Integer id;
    private String content;
    private AuthorDto author;

    private CommentDto(Builder builder) {
        this.id = builder.id;
        this.content = builder.content;
        this.author = builder.author;
    }

    public static class Builder {
        private Integer id;
        private String content;
        private AuthorDto author;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder author(User author) {
            this.author = new AuthorDto(author.getId(), author.getUsername());
            return this;
        }

        public CommentDto build() {
            return new CommentDto(this);
        }
    }

}
