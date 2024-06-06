package com.example.database.repository;

import com.example.database.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findAllByReceiver(String receiver);
    Notification findOneByNotificationId(String notificationId);
    List<Notification> findAllByReceiverAndStatus(String receiver, Boolean status);
}
