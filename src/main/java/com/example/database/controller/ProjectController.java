package com.example.database.controller;

import com.example.database.dto.ProjectDTO;
import com.example.database.entity.Project;
import com.example.database.service.Project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/create-project")
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectService.createProject(projectDTO);
        if(project == null) {
            return new ResponseEntity<>("Project existed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Project created success", HttpStatus.CREATED);
    }
}