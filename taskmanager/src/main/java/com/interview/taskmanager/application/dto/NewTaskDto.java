package com.interview.taskmanager.application.dto;

public record NewTaskDto(String title, String description, String status,
                      String priority) {

}