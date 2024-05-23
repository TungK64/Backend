package com.example.database.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Notification")
public class Notification {
    @Id
    private String notificationId;
    private String type;
    private String reporter;
    private String receiver;
    private String taskId;
    private String time;
    private String message;
    private Boolean status;
}
