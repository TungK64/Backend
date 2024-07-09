package com.example.database.controller;

import com.example.database.dto.LoginDTO;
import com.example.database.dto.UserDTO;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import com.example.database.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create-user/{role}")
    public ResponseEntity<?> signUpUser(@RequestBody Map<String, String> user, @PathVariable String role) {
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

    @GetMapping("/get-project/{userNumber}")
    public ResponseEntity<?> getProjectList(@PathVariable String userNumber) {
        List<Project> projects = userService.getProjects(userNumber);
        if(projects.isEmpty()) {return new ResponseEntity<>("No projects found", HttpStatus.BAD_REQUEST);}
        else {
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
    }

    @GetMapping("/get-lectures-name/number")
    public ResponseEntity<?> getLecturesName(@RequestParam String list) {
        List<String> lecName = userService.getLectureName(list);
        if(lecName.isEmpty()) {
            return new ResponseEntity<>("No lectures found", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(lecName, HttpStatus.OK);
        }
    }

    @GetMapping("/get-user/{userNumber}/{role}")
    public ResponseEntity<?> getUser(@PathVariable String userNumber, @PathVariable String role) {
        UserDTO userDTO = userService.getUser(userNumber, role);
        if(userDTO == null) {return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);}
        else return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/add-class/{classCode}/{userNumber}/{role}")
    public ResponseEntity<?> addClass(@PathVariable String classCode, @PathVariable String userNumber, @PathVariable String role) {
        String res = userService.addClassByClassCode(classCode, userNumber, role);
        if(Objects.equals(res, "Successfully added class")) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-lecturer/{topicId}")
    public ResponseEntity<?> getLecturerByTopicId(@PathVariable String topicId) {
        String res = userService.getLectureByTopicId(topicId);
        if(res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can't find lecturer of this topic", HttpStatus.BAD_REQUEST);
        }
    }
}
