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
    private Integer projectId;
    private String projectName;
    private List<Integer> lectureList;
    private List<Integer> studentList;
    private Integer classCode;
}
