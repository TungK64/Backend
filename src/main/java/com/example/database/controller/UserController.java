package com.example.database.controller;

import com.example.database.dto.LoginDTO;
import com.example.database.dto.UserDTO;
import com.example.database.entity.User;
import com.example.database.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-user/{role}")
    public ResponseEntity<?> signUpUser(@RequestBody User user,@PathVariable String role) {
        UserDTO createdUser = userService.createUser(user, role);
        if(createdUser == null) {
            return new ResponseEntity<>("Created failed, user have been existed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/view-project/{lecNumber}")
    public  ResponseEntity<?> viewProject(@PathVariable String lecNumber) {
        List<String> projectList = userService.viewProjectLecture(lecNumber);
        if(projectList.isEmpty()) {
            return new ResponseEntity<>("You have not been assigned to instruct any class yet", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(projectList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/view-student/{lecNumber}")
    public  ResponseEntity<?> viewStudentList(@PathVariable String lecNumber) {
        List<String> studentList = userService.viewStudentByLecture(lecNumber);
        if(studentList.isEmpty()) {
            return new ResponseEntity<>("You have not been assigned any students yet", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentList, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO);
        if(user == null) {
            return new ResponseEntity<>("Wrong email or password", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
}
