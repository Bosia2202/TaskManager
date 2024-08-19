package com.interview.taskmanager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.comment.CommentDto;
import com.interview.taskmanager.common.dto.profile.OwnerTaskDto;
import com.interview.taskmanager.common.dto.profile.UserProfile;
import com.interview.taskmanager.common.dto.task.TaskBriefInfoDto;
import com.interview.taskmanager.common.dto.task.TaskDetails;
import com.interview.taskmanager.common.dto.task.TaskDto;
import com.interview.taskmanager.domain.services.task.TaskManagementService;

import jakarta.persistence.NoResultException;


import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ApiController {

    private TaskManagementService taskManagementService;

    public ApiController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    @PutMapping("/task/create")
    public ResponseEntity<HttpStatus> createTask(@RequestBody TaskDetails taskDetails, Principal principal) {
        taskManagementService.createTask(taskDetails, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/task/update")
    public ResponseEntity<HttpStatus> updateTask(@RequestParam("taskId") Integer taskId,
            @RequestBody TaskDetails taskDetails, Principal principal) {
        taskManagementService.updateTaskById(taskId, taskDetails, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/task/delete")
    public ResponseEntity<HttpStatus> deleteTaskById(@RequestParam("id") Integer id, Principal principal) {
        taskManagementService.removeTaskById(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("task/executor/add")
    public ResponseEntity<HttpStatus> addExecutor(@RequestParam("taskId") Integer taskId,
            @RequestParam("executorId") Integer executorId, Principal principal) {
        taskManagementService.addExecutor(taskId, executorId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("task/executor/delete")
    public ResponseEntity<HttpStatus> deleteExecutor(@RequestParam("taskId") Integer taskId,
            @RequestParam("executorId") Integer executorId, Principal principal) {
        taskManagementService.removeExecutor(taskId, executorId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/task/find")
    public ResponseEntity<TaskDto> findById(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(taskManagementService.findById(id), HttpStatus.OK);
    }

    @GetMapping("task/searchByTitle")
    public ResponseEntity<List<TaskBriefInfoDto>> searchByTitle(@RequestParam("title") String title,
            @RequestParam("page") Integer page) {
        return new ResponseEntity<>(taskManagementService.findAllTasksByTitle(title, page), HttpStatus.OK);
    }

    @GetMapping("task/assigned")
    public ResponseEntity<List<OwnerTaskDto>> getUserTask(Principal principal) {
        return new ResponseEntity<>(taskManagementService.getAssignedTasksList(principal), HttpStatus.OK);
    }

    @PutMapping("comment/create")
    public ResponseEntity<HttpStatus> createComment(@RequestBody CommentDetails commentDetails, Principal principal) {
        taskManagementService.createComment(commentDetails, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("comment/update")
    public ResponseEntity<HttpStatus> updateComment(@RequestParam("commentId") Integer commentId,
            @RequestBody CommentDetails commentDetails,
            Principal principal) {
        taskManagementService.updateComment(commentId, commentDetails, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("comment/delete")
    public ResponseEntity<HttpStatus> deleteComment(@RequestParam("commentId") Integer commentId, Principal principal) {
        taskManagementService.removeComment(commentId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("comment/getComments")
    public ResponseEntity<List<CommentDto>> getComments(@RequestParam("taskId") Integer id,
            @RequestParam("page") Integer page) {
        return new ResponseEntity<>(taskManagementService.getCommentsByTaskId(id, page), HttpStatus.OK);
    }

    @GetMapping("user/{param}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable("param") String param) {
        if (param.matches("\\d+")) {
            return new ResponseEntity<>(taskManagementService.getUserProfileById(Integer.parseInt(param)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(taskManagementService.getUserProfileByUsername(param), HttpStatus.OK);
        }
    }

    @ExceptionHandler
    private ResponseEntity<String> noResultException(NoResultException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<String> accessDenied(AccessDeniedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

}