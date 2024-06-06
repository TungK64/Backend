package com.example.database.service.Notification;

import com.example.database.entity.Notification;

import java.util.List;
import java.util.Map;

public interface NotificationService {
    Map<Integer, List<?>> getAllNotification(String receiver);

    void changeStatusNoti(String notiId);

    Notification rejectSuggestTopic(String receiver, String reporter, String topicName, String notificationId);

    Notification acceptSuggestTopic(String receiver, String reporter, String topicName, String description, String classCode, String notificationId);
}
