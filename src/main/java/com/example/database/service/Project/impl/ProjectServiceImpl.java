package com.example.database.service.Project.impl;

import com.example.database.dto.ProjectDTO;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import com.example.database.repository.ProjectRepository;
import com.example.database.repository.UserRepository;
import com.example.database.service.Project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Project createProject(ProjectDTO projectInfo) {

        Project project1 = projectRepository.findByProjectNameAndClassCode(projectInfo.getProjectName(), projectInfo.getClassCode());
        if(project1 != null) {
            return null;
        }
        List<String> stuNumber = new ArrayList<>();
        Project project = new Project();

        project.setProjectName(projectInfo.getProjectName());

        User lecture = userRepository.findByRoleAndProjectListContaining("Lecture", projectInfo.getClassCode());

        if(lecture != null) {
            project.setLectureNumber(lecture.getUserNumber());
        }


        List<User> students = userRepository.findAllByRoleAndProjectListContains("Student", projectInfo.getClassCode());
        if(!students.isEmpty()) {
            for(User student : students) {
                stuNumber.add(student.getUserNumber());
            }
            project.setStudentList(stuNumber);
        }
        project.setClassCode(projectInfo.getClassCode());

        projectRepository.save(project);

        return project;
    }
}
