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
@Document(collection = "Topic")
public class Topic {
    @Id
    private Integer topicId;
    private String topicName;
    private Integer projectId;
    private Integer lectureId;
    private String description;
    private List<Integer> studentList;
}
