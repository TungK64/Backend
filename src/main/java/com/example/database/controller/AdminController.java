package com.example.database.controller;

import com.example.database.dto.AdminDTO;
import com.example.database.dto.LoginDTO;
import com.example.database.dto.UserDTO;
import com.example.database.entity.Admin;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import com.example.database.repository.AdminRepository;
import com.example.database.service.Project.ProjectService;
import com.example.database.service.User.AdminService;
import com.example.database.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/v1")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/admin-login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Admin admin = adminService.adminLogin(loginDTO);
        if(admin == null) {
            return new ResponseEntity<>("Wrong email or password", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminDTO adminDTO) {
        Admin admin = adminService.createAdmin(adminDTO);
        if(admin == null) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-all/{role}")
    public ResponseEntity<?> getAllUserByRole(@PathVariable String role) {
        List<UserDTO> userDTOList = userService.getAllUser(role);
        if(userDTOList == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(userDTOList, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-all-project")
    public ResponseEntity<?> getAllProject() {
        Map<Integer, List<Object>> projectList = projectService.getAllProject();
        if(projectList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(projectList, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-all-lecture-name/{lectureNumberList}")
    public ResponseEntity<?> getLectureName(@PathVariable List<String> lectureNumberList) {
        List<User> lecNameList = userService.getLectureNameByLecNumber(lectureNumberList);
        if(lecNameList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(lecNameList, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/upload-excel/{role}")
    public ResponseEntity<?> uploadExcel(@PathVariable String role, @RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if(Objects.equals(role, "Student")) {
            adminService.addExcelUser(file, "Student");
        } else {
            adminService.addExcelUser(file, "Lecture");
        }
        return new ResponseEntity<>("Imported file successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/upload-class-excel")
    public ResponseEntity<?> uploadClassExcel(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        adminService.addExcelClass(file);
        return new ResponseEntity<>("Imported file successfully", HttpStatus.OK);
    }
}
