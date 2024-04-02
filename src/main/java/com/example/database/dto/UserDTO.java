package com.example.database.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Integer userId;
    private String email;
    private String userName;
    private String phoneNumber;
    private String role;
    private List<Integer> projectList;
    private List<Integer> topicList;
    private String userNumber;
}
