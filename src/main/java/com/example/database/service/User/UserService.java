package com.example.database.service.User;

import com.example.database.dto.UserDTO;
import com.example.database.entity.User;

import java.util.List;

public interface UserService {
    public UserDTO createUser(User user, String role);

    public List<String> viewProjectLecture(String lectureNumber);

    public List<String> viewStudentByLecture(String lectureNumber);
}
