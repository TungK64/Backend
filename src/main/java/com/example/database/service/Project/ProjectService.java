package com.example.database.service.Project;

import com.example.database.dto.ProjectDTO;
import com.example.database.entity.Project;
import com.example.database.entity.User;

import java.util.List;

public interface ProjectService {
    public Project createProject(ProjectDTO project);

    public List<User> getStudentByProjectIdAndRole(String projectId, String role);
}
