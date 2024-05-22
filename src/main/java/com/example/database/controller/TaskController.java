package com.example.database.controller;

import com.example.database.dto.ProjectDTO;
import com.example.database.entity.Task;
import com.example.database.service.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/create-task/{topicId}/{reporter}/{assignee}")
    public ResponseEntity<?> createTask(@RequestBody Task task, @PathVariable String topicId, @PathVariable String reporter, @PathVariable String assignee) {
        Task newTask = taskService.createTask(task, topicId, reporter, assignee);
        if(newTask != null) {
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Create task failed" ,HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-task/{topicId}/{status}/{userNumber}")
    public ResponseEntity<?> getTask(@PathVariable String status, @PathVariable String userNumber, @PathVariable String topicId) {
        List<Task> taskList;
        taskList = taskService.getTaskByStatus(status, userNumber, topicId);
        if(taskList.isEmpty()) {
            return new ResponseEntity<>("No task found" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @PutMapping("/change-status/{taskId}/{newStatus}")
    public ResponseEntity<?> changeStatus(@PathVariable String taskId, @PathVariable String newStatus) {
        taskService.changeTaskStatus(taskId, newStatus);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
