package com.example.database.controller;

import com.example.database.dto.AdminDTO;
import com.example.database.dto.LoginDTO;
import com.example.database.entity.Admin;
import com.example.database.entity.User;
import com.example.database.repository.AdminRepository;
import com.example.database.service.User.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

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
}
