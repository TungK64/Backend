package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Task")
public class Task {
    @Id
    private String taskID;
    private String taskName;
    private String status;
    private String description;
    private List<byte[]> attachments;
    private LocalDate start;
    private LocalDate update;
    private LocalDate deadline;
    private String topicId;
    private String reporter;
    private String assignee;
    private List<Comment> comment;
}
