package com.example.database.service.Notification.impl;

import com.example.database.entity.*;
import com.example.database.repository.*;
import com.example.database.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<Integer, List<?>> getAllNotification(String receiver) {
        Map<Integer, List<?>> notificationMap = new HashMap<>();
        List<Notification> notificationList = notificationRepository.findAllByReceiver(receiver);
        List<String> taskNameList = new ArrayList<>();;
        List<String> projectNameList = new ArrayList<>();
        if(!notificationList.isEmpty()) {
            for(Notification notification : notificationList) {
                if(!notification.getType().equals("suggest topic")) {
                    String taskId = notification.getTaskId();
                    Task task = taskRepository.findOneByTaskID(taskId);
                    if(task != null) {
                        taskNameList.add(task.getTaskName());
                        String topicId = task.getTopicId();
                        Topic topic = topicRepository.findTopicByTopicId(topicId);
                        if(topic != null) {
                            String projectId = topic.getProjectId();
                            Project project = projectRepository.findByProjectId(projectId);
                            if(project != null) {
                                projectNameList.add(project.getProjectName());
                            }
                        }
                    }
                    else {
                        taskNameList.add("");
                        projectNameList.add("");
                    }
                } else {
                    taskNameList.add("");
                    projectNameList.add("");
                }
            }
        }
        notificationMap.put(0, notificationList);
        notificationMap.put(1, taskNameList);
        notificationMap.put(2, projectNameList);
        return notificationMap;
    }

    @Override
    public void changeStatusNoti(String notiId) {
        Notification notification = notificationRepository.findOneByNotificationId(notiId);
        if(notification != null) {
            notification.setStatus(true);
            notificationRepository.save(notification);
        }
    }

    @Override
    public Notification rejectSuggestTopic(String receiver, String reporter, String topicName, String notificationId) {
        Notification notification = new Notification();
        notification.setReceiver(reporter);
        notification.setReporter(receiver);
        notification.setMessage("Your topic you suggest: " + topicName + " was rejected.");
        notification.setType("reject");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedDate = now.format(formatter);
        notification.setTime(formattedDate);
        notification.setStatus(false);
        notificationRepository.save(notification);
        Notification notification1 = notificationRepository.findOneByNotificationId(notificationId);
        if(notification1 != null) {
            notificationRepository.delete(notification1);
        }
        return notification;
    }

    @Override
    public Notification acceptSuggestTopic(String receiver, String reporter, String topicName, String description, String classCode, String notificationId)  {
        Notification notification = new Notification();
        Topic topic = new Topic();
        Project project = projectRepository.findByClassCode(Integer.parseInt(classCode));

        topic.setTopicName(topicName);
        topic.setDescription(description);
        if(project != null) {
            topic.setProjectId(project.getProjectId());
        }
        if(topic.getStudentList() != null && !topic.getStudentList().isEmpty()) {
            topic.getStudentList().add(reporter);
        } else {
            topic.setStudentList(new ArrayList<>());
            topic.getStudentList().add(reporter);
        }
        topicRepository.save(topic);
        User student = userRepository.findByUserNumber(reporter);
        if(student != null) {
            if(student.getTopicList() != null && !student.getTopicList().isEmpty()) {
                student.getTopicList().add(topic.getTopicId());
            } else {
                student.setTopicList(new ArrayList<>());
                student.getTopicList().add(topic.getTopicId());
            }
        }

        notification.setReceiver(reporter);
        notification.setReporter(receiver);
        notification.setType("accept");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedDate = now.format(formatter);
        notification.setTime(formattedDate);
        notification.setStatus(false);
        notification.setMessage("Your topic you suggest: " + topicName + " was accepted.");
        notificationRepository.save(notification);
        Notification notification1 = notificationRepository.findOneByNotificationId(notificationId);
        if(notification1 != null) {
            notificationRepository.delete(notification1);
        }
        return notification;
    }
}
