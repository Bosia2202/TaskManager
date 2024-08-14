package com.interview.taskmanager.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentDetails {
    private Integer taskId;
    private String content;
    
}
