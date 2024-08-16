package com.interview.taskmanager.common.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {
    private Integer id;
    private String username;
}
