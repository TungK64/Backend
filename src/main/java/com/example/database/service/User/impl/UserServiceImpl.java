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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;


    @Override
    public UserDTO createUser(Map<String, String> userInfo, String role) {

        for(User u : userRepository.findAll()) {
            if(userInfo.get("userName").equals(u.getUserNumber()) || userInfo.get("email").equals(u.getEmail())) {
                return null;
            }
        }

        User user = new User();
        if(role.equals("Student")) {
            user.setRole("Student");
        } else {
            user.setRole("Lecture");
        }
        user.setUserName(userInfo.get("userName"));
        user.setEmail(userInfo.get("email"));

        String dateOfBirth = userInfo.get("dateOfBirth");
        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfBirth, localDateFormatter);
        user.setDateOfBirth(date);

        user.setPassword(userInfo.get("userNumber"));
        user.setPhoneNumber(userInfo.get("phoneNumber"));

        String projectListString = userInfo.get("projectList");
        String[] stringNumbers = projectListString.split(",");

        List<Integer> intNumbers = new ArrayList<>();

        for (String stringNumber : stringNumbers) {
            intNumbers.add(Integer.parseInt(stringNumber.trim()));
        }
        user.setProjectList(intNumbers);

        user.setUserNumber(userInfo.get("userNumber"));
        userRepository.save(user);

        List<Project> projects = projectRepository.findByClassCodeIn(intNumbers);
        if(!projects.isEmpty()) {
            for(Project project : projects) {
                if(role.equals("Student")) {
                    List<String> studentList = project.getStudentList();
                    if (studentList == null) {
                        studentList = new ArrayList<>(); // Initialize studentList
                        project.setStudentList(studentList);
                    }
                    studentList.add(userInfo.get("userNumber"));
                } else {
                    project.setLectureNumber(userInfo.get("userNumber"));
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
        createdUser.setDateOfBirth(user.getDateOfBirth());
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
            userDTO.setDateOfBirth(user.getDateOfBirth());
            return userDTO;
        }
    }

    @Override
    public List<User> getLectureNameByLecNumber(List<String> lecNumber) {
        List<User> lecNames = new ArrayList<>();
        for(String str :lecNumber) {
            User lecture = userRepository.findByUserNumberAndRole(str, "Lecture");
            lecNames.add(Objects.requireNonNullElseGet(lecture, User::new));
        }
        return lecNames;
    }

    @Override
    public List<UserDTO> getAllUser(String role) {
        List<User> users = userRepository.findAllByRole(role);
        if(users.isEmpty()) {return null;}
        else {
            List<UserDTO> userDTOList = new ArrayList<>();
            for(User user : users) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserName(user.getUserName());
                userDTO.setRole(role);
                userDTO.setEmail(user.getEmail());
                userDTO.setUserNumber(user.getUserNumber());
                userDTO.setProjectList(user.getProjectList());
                userDTO.setTopicList(user.getTopicList());
                userDTO.setPhoneNumber(user.getPhoneNumber());
                userDTO.setDateOfBirth(user.getDateOfBirth());
                userDTOList.add(userDTO);
            }
            return userDTOList;
        }
    }
}
