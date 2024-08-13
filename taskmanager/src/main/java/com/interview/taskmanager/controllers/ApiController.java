package com.interview.taskmanager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.taskmanager.common.dto.CommentDetails;
import com.interview.taskmanager.common.dto.TaskDetails;
import com.interview.taskmanager.common.dto.TaskFullInfoDto;
import com.interview.taskmanager.domain.services.task.TaskManagementService;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PatchMapping("/task/path")
    public ResponseEntity<HttpStatus> updateTask(@RequestParam("taskId") Integer taskId,
            @RequestBody TaskDetails taskDetails, Principal principal) {
        taskManagementService.updateTaskById(taskId, taskDetails, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/task/delete")
    public ResponseEntity<HttpStatus> deleteTaskById(@RequestParam("id") Integer id, Principal principal) {
        taskManagementService.deleteTaskById(id, principal);
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
            @RequestParam("userId") Integer userId, Principal principal) {
        taskManagementService.deleteExecutor(taskId, userId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/task/search")
    public ResponseEntity<TaskFullInfoDto> searchById(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(taskManagementService.findByIdWithAllInfo(id), HttpStatus.OK);
    }

    @GetMapping("/task/searchAll")
    public ResponseEntity<List<TaskDetails>> searchByTitle(@RequestParam("title") String title) {
        return new ResponseEntity<>(taskManagementService.findAllTasksByTitleBriefInfo(title), HttpStatus.OK);
    }

    @GetMapping("/task/myTask")
    public ResponseEntity<List<TaskDetails>> getUserTask(Principal principal) {
        return new ResponseEntity<>(taskManagementService.getAssignedTasksList(principal), HttpStatus.OK);
    }

    @PutMapping("/comment/create")
    public ResponseEntity<HttpStatus> createComment(@RequestParam("taskId") Integer taskId,
            @RequestBody CommentDetails commentDetails, Principal principal) {
        taskManagementService.createComment(taskId, commentDetails, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/comment/update")
    public ResponseEntity<HttpStatus> updateComment(@RequestParam("taskId") Integer taskId,
            @RequestParam("commentId") Integer commentId, @RequestBody CommentDetails commentDetails,
            Principal principal) {
        taskManagementService.updateComment(taskId, commentId, commentDetails, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<HttpStatus> deleteComment(@RequestParam("commentId") Integer commentId, Principal principal) {
        taskManagementService.deleteComment(commentId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
