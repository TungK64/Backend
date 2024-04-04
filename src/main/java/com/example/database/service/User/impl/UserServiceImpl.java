package com.example.database.service.User.impl;


import com.example.database.dto.UserDTO;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import com.example.database.repository.ProjectRepository;
import com.example.database.repository.UserRepository;
import com.example.database.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;


    @Override
    public UserDTO createStudent(User userInfo) {

        for(User u : userRepository.findAll()) {
            if(userInfo.getUserNumber().equals(u.getUserNumber()) || userInfo.getEmail().equals(u.getEmail())) {
                return null;
            }
        }

        User user = new User();
        user.setRole("Student");
        user.setUserName(userInfo.getUserName());
        user.setEmail(userInfo.getEmail());
        user.setPassword(userInfo.getUserNumber());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setProjectList(userInfo.getProjectList());
        user.setTopicList(userInfo.getTopicList());
        user.setUserNumber(userInfo.getUserNumber());
        userRepository.save(user);

        UserDTO createdStudent = new UserDTO();
        createdStudent.setEmail(user.getEmail());
        createdStudent.setUserName(user.getUserName());
        createdStudent.setRole(user.getRole());
        createdStudent.setUserNumber(user.getUserNumber());
        createdStudent.setProjectList(user.getProjectList());
        createdStudent.setTopicList(user.getTopicList());
        createdStudent.setPhoneNumber(user.getPhoneNumber());

        return createdStudent;
    }

    @Override
    public UserDTO createLecture(User userInfo) {

        for(User u : userRepository.findAll()) {
            if(userInfo.getUserNumber().equals(u.getUserNumber()) || userInfo.getEmail().equals(u.getEmail())) {
                return null;
            }
        }

        User user = new User();
        user.setRole("Lecture");
        user.setUserName(userInfo.getUserName());
        user.setEmail(userInfo.getEmail());
        user.setPassword(userInfo.getUserNumber());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setProjectList(userInfo.getProjectList());
        user.setTopicList(userInfo.getTopicList());
        user.setUserNumber(userInfo.getUserNumber());
        userRepository.save(user);

        UserDTO createdLecture = new UserDTO();
        createdLecture.setEmail(user.getEmail());
        createdLecture.setUserName(user.getUserName());
        createdLecture.setRole(user.getRole());
        createdLecture.setUserNumber(user.getUserNumber());
        createdLecture.setProjectList(user.getProjectList());
        createdLecture.setTopicList(user.getTopicList());
        createdLecture.setPhoneNumber(user.getPhoneNumber());

        return createdLecture;
    }

    @Override
    public List<String> viewProjectLecture(String lectureNumber) {
        List<String> projectList = new ArrayList<>();
        for(User lec : userRepository.findAllByRole("Lecture")) {
            if(lec.getUserNumber().equals(lectureNumber)) {
                List<Integer> projectCode = lec.getProjectList();
                for(int code : projectCode) {
                    for(Project project : projectRepository.findAll()) {
                        if(project.getClassCode().equals(code)) {
                            projectList.add(project.getProjectName().concat(" - ").concat(project.getClassCode().toString()));
                        }
                    }
                }
            }
        }
        return projectList;
    }

    @Override
    public List<String> viewStudentByLecture(String lectureNumber) {
        List<String> studentList = new ArrayList<>();
        for(User lec : userRepository.findAllByRole("Lecture")) {
            if(lec.getUserNumber().equals(lectureNumber)) {
            }
        }
        return null;
    }
}
