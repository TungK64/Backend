package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.time.LocalDateTime;
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
    private LocalDateTime start;
    private LocalDateTime update;
    private LocalDateTime deadline;
    private Integer reporter;
    private Integer assignee;
    private List<String> comment;
    private Integer topicId;
}
