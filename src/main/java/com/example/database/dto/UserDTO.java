package com.example.database.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDTO {
    private String email;
    private String userName;
    private String phoneNumber;
    private String role;
    private List<Integer> projectList;
    private List<String> topicList;
    private String userNumber;
    private LocalDate dateOfBirth;
}
