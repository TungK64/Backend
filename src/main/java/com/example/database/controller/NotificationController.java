package com.example.database.controller;


import com.example.database.entity.Notification;
import com.example.database.repository.NotificationRepository;
import com.example.database.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/get-notification/{receiver}")
    public ResponseEntity<?> getNotification(@PathVariable String receiver) {
        Map<Integer, List<?> >  notificationMap = notificationService.getAllNotification(receiver);
        if(notificationMap.get(0).isEmpty()) {
            return new ResponseEntity<>( null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(notificationMap, HttpStatus.OK);
        }
    }

    @PutMapping("/change-status-noti/{Id}")
    public ResponseEntity<?> changeNotificationStatus(@PathVariable String Id) {
        notificationService.changeStatusNoti(Id);
        return new ResponseEntity<>( "Change status success", HttpStatus.OK);
    }

    @GetMapping("/get-unread-noti/{receiver}")
    public ResponseEntity<?> getUnreadNotification(@PathVariable String receiver) {
        List<Notification> unreadNotificationList = notificationRepository.findAllByReceiverAndStatus(receiver, false);
        if(unreadNotificationList.isEmpty()) {
            return new ResponseEntity<>( 0, HttpStatus.OK);
        }
        return new ResponseEntity<>(unreadNotificationList.toArray().length, HttpStatus.OK);
    }

    @PostMapping("/reject-topic/{receiver}/{reporter}/{notificationId}")
    public ResponseEntity<?> rejectTopic(@PathVariable String receiver, @PathVariable String reporter, @RequestBody String topicName, @PathVariable String notificationId) {
        Notification notification = notificationService.rejectSuggestTopic(receiver, reporter, topicName, notificationId);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @PostMapping("/accept-topic/{receiver}/{reporter}/{notificationId}")
    public ResponseEntity<?> acceptTopic(@PathVariable String receiver, @PathVariable String reporter, @RequestBody Map<String, String> topicInfo, @PathVariable String notificationId) {
        Notification notification = notificationService.acceptSuggestTopic(receiver, reporter,
                topicInfo.get("topicName"), topicInfo.get("description"), topicInfo.get("classCode"), notificationId);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
}
