package com.example.database.service.User;

import com.example.database.dto.LoginDTO;
import com.example.database.dto.UserDTO;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    public UserDTO createUser(Map<String, String> userInfo, String role);

    public List<String> viewProjectLecture(String lectureNumber);

    public List<String> viewStudentByLecture(String lectureNumber);

    public User login(LoginDTO loginDTO);

    public List<Project> getProjects(String userNumber);

    public List<String> getLectureName(String lecNumber);

    public UserDTO getUser(String userNumber, String role);

    List<User> getLectureNameByLecNumber(List<String> lecNumber);

    List<UserDTO> getAllUser(String role);

    String addClassByClassCode(String classCode, String userNumber, String role);
}
