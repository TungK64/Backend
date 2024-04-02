package com.example.database.service.User;

import com.example.database.dto.UserDTO;
import com.example.database.entity.User;

public class UserServiceImpl implements UserService{

    @Override
    public UserDTO createUser(UserDTO loginDTO) {
        User user = new User();
        user.setUserName(loginDTO.getUserName());
        user.setUserNumber(loginDTO.getUserNumber());
        user.setEmail(loginDTO.getEmail());
        user.setRole(loginDTO.getRole());
        user.setPhoneNumber(loginDTO.getPhoneNumber());
        user.setProjectList(loginDTO.getProjectList());
        user.setTopicList(loginDTO.getTopicList());
        return null;
    }
}
