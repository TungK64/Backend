package com.example.database.controller;

import com.example.database.dto.ProjectDTO;
import com.example.database.entity.Attachments;
import com.example.database.entity.Task;
import com.example.database.repository.TaskRepository;
import com.example.database.service.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create-task/{topicId}/{reporter}/{assignee}")
    public ResponseEntity<?> createTask(@RequestBody Map<String, String> task, @PathVariable String topicId, @PathVariable String reporter, @PathVariable String assignee) {
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

    @PostMapping("/add-description/{taskId}")
    public ResponseEntity<?> addDescription(@PathVariable String taskId, @RequestBody Map<String, String> description) {
        taskService.addTaskDescription(taskId, description);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/set-prio/{taskId}/{userNumber}/{priority}")
    public ResponseEntity<?> setPriority(@PathVariable String taskId, @PathVariable String priority, @PathVariable String userNumber) {
        taskService.setPriority(taskId, userNumber, priority);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/attachments/{taskId}/{userNumber}/{receiver}")
    public ResponseEntity<?> addAttachment(@PathVariable String taskId, @PathVariable String userNumber, @PathVariable String receiver, @RequestBody List<Attachments> attachList) {
        taskService.addAttachments(attachList, taskId, userNumber, receiver);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/comment/{taskId}/{userNumber}/{receiver}")
    public ResponseEntity<?> addComment(@PathVariable String taskId, @PathVariable String userNumber, @PathVariable String receiver, @RequestBody String comment) {
        taskService.addComment(comment ,taskId, userNumber, receiver);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/deadline/{taskId}/{userNumber}/{receiver}")
    public ResponseEntity<?> changeDeadline(@PathVariable String taskId, @PathVariable String userNumber, @PathVariable String receiver, @RequestBody Map<String, String> deadline) {
        taskService.changeDeadline(taskId, userNumber, receiver, deadline);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/get-task-by-id/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable String taskId) {
        Task task = taskRepository.findOneByTaskID(taskId);
        if(task == null) {
            return new ResponseEntity<>("Task not found" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
