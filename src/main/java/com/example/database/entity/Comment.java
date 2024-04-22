package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Comment")
public class Comment {
    @Id
    private String commentId;
    private String taskID;
    private String topicId;
    private String projectID;
    private String CommentatorNumber;
    private LocalDateTime commentDate;
}
