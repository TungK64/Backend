package com.example.database.service.User.impl;


import com.example.database.dto.LoginDTO;
import com.example.database.dto.UserDTO;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import com.example.database.repository.ProjectRepository;
import com.example.database.repository.UserRepository;
import com.example.database.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;


    @Override
    public UserDTO createUser(User userInfo, String role) {

        for(User u : userRepository.findAll()) {
            if(userInfo.getUserNumber().equals(u.getUserNumber()) || userInfo.getEmail().equals(u.getEmail())) {
                return null;
            }
        }

        User user = new User();
        if(role.equals("Student")) {
            user.setRole("Student");
        } else {
            user.setRole("Lecture");
        }
        user.setUserName(userInfo.getUserName());
        user.setEmail(userInfo.getEmail());
        user.setPassword(userInfo.getUserNumber());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setProjectList(userInfo.getProjectList());
        user.setTopicList(userInfo.getTopicList());
        user.setUserNumber(userInfo.getUserNumber());
        userRepository.save(user);

        List<Project> projects = projectRepository.findByClassCodeIn(userInfo.getProjectList());
        if(!projects.isEmpty()) {
            for(Project project : projects) {
                if(role.equals("Student")) {
                    List<String> studentList = project.getStudentList();
                    if (studentList == null) {
                        studentList = new ArrayList<>(); // Initialize studentList
                        project.setStudentList(studentList);
                    }
                    studentList.add(userInfo.getUserNumber());
                } else {
                    project.setLectureNumber(userInfo.getUserNumber());
                }
                projectRepository.save(project);
            }
        }
        UserDTO createdUser = new UserDTO();
        createdUser.setEmail(user.getEmail());
        createdUser.setUserName(user.getUserName());
        createdUser.setRole(user.getRole());
        createdUser.setUserNumber(user.getUserNumber());
        createdUser.setProjectList(user.getProjectList());
        createdUser.setTopicList(user.getTopicList());
        createdUser.setPhoneNumber(user.getPhoneNumber());

        return createdUser;
    }



    @Override
    public List<String> viewProjectLecture(String lectureNumber) {
        List<String> projectList = new ArrayList<>();
        User lecture = userRepository.findByUserNumberAndRole(lectureNumber, "Lecture");
        if (lecture != null) {
            List<Integer> projectCodes = lecture.getProjectList();
            List<Project> projects = projectRepository.findByClassCodeIn(projectCodes);
            for (Project project : projects) {
                projectList.add(project.getProjectName() + " - " + project.getClassCode());
            }
        }

        return projectList;
    }


    @Override
    public List<String> viewStudentByLecture(String lectureNumber) {
        List<String> studentList = new ArrayList<>();
        User lecture = userRepository.findByUserNumberAndRole(lectureNumber, "Lecture");
        if (lecture != null) {
            List<Integer> classCodes = lecture.getProjectList();
            List<User> students = userRepository.findAllByRoleAndProjectListContaining("Student", classCodes);
            for (User student : students) {
                studentList.add(student.getUserName() + " - MSSV: " + student.getUserNumber());
            }
        }
        return studentList;
    }

    @Override
    public User login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if(user == null) {return null;}
        else {
            if(user.getPassword().equals(loginDTO.getPassword())) {
                return user;
            }
            else return null;
        }
    }

    @Override
    public List<Project> getProjects(String userNumber) {
        User user = userRepository.findByUserNumber(userNumber);
        List<Project> projects = new ArrayList<>();
        if(user == null) {return null;}
        else {
            if(user.getProjectList() != null) {
                projects = projectRepository.findByClassCodeIn(user.getProjectList());
                return projects;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<String> getLectureName(String lecNumber) {
        List<String> lecNames = new ArrayList<>();
        String[] lecNumberList = lecNumber.split(",");
        for(String str :lecNumberList) {
            User lecture = userRepository.findByUserNumberAndRole(str, "Lecture");
            if(lecture != null) {
                lecNames.add(lecture.getUserName());
            }
            else {
                lecNames.add("");
            }

        }
        return lecNames;
    }

    @Override
    public UserDTO getUser(String userNumber, String role) {
        User user = userRepository.findByUserNumberAndRole(userNumber, role);
        if(user == null) {return null;}
        else {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
            userDTO.setRole(role);
            userDTO.setEmail(user.getEmail());
            userDTO.setUserNumber(user.getUserNumber());
            userDTO.setProjectList(user.getProjectList());
            userDTO.setTopicList(user.getTopicList());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            return userDTO;
        }
    }
}
