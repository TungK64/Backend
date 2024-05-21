package com.example.database.controller;

import com.example.database.dto.ProjectDTO;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import com.example.database.repository.ProjectRepository;
import com.example.database.service.Project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/create-project")
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectService.createProject(projectDTO);
        if(project == null) {
            return new ResponseEntity<>("Project existed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Project created success", HttpStatus.CREATED);
    }

    @GetMapping("/get-student-by-projectId/{projectId}/{role}")
    public ResponseEntity<?> getStudentByProjectId(@PathVariable String projectId, @PathVariable String role) {
        List<User> students = projectService.getStudentByProjectIdAndRole(projectId, role);
        if(students.isEmpty()) {return new ResponseEntity<>("No student found", HttpStatus.NOT_FOUND);}
        else {return new ResponseEntity<>(students, HttpStatus.OK);}
    }

    @GetMapping("/get-project-by-id/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        if(project == null) {
            return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
        } else {return new ResponseEntity<>(project, HttpStatus.OK);}
    }
}
