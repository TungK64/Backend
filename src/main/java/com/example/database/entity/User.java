package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "User")
public class User {
    @Id
    private Integer userId;
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private String role;
    private List<Integer> projectList;
    private List<Integer> topicList;
    private String userNumber;
}
