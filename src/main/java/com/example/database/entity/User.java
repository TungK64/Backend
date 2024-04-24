package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "User")
public class User {
    @Id
    @Generated()
    private String userId;
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private String role;
    private LocalDate dateOfBirth;
    private List<Integer> projectList;
    private List<String> topicList;
    private String userNumber;
}
