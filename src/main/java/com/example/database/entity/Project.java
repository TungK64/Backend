package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Project")
public class Project {
    @Id
    private String projectId;
    private String projectName;
    private List<String> lectureList;
    private List<String> studentList;
    private Integer classCode;
}
