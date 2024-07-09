package com.example.database.service.Topic.impl;

import com.example.database.dto.TopicDTO;
import com.example.database.entity.Notification;
import com.example.database.entity.Project;
import com.example.database.entity.Topic;
import com.example.database.entity.User;
import com.example.database.repository.NotificationRepository;
import com.example.database.repository.ProjectRepository;
import com.example.database.repository.TopicRepository;
import com.example.database.repository.UserRepository;
import com.example.database.service.Topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Topic createTopic(TopicDTO topicDTO, String projectID) {
        Topic topic = new Topic();
        topic.setTopicName(topicDTO.getTopicName());
        topic.setDescription(topicDTO.getTopicDescription());
        topic.setProjectId(projectID);
        topicRepository.save(topic);
        return topic;
    }

    @Override
    public List<Topic> getTopicList(String projectID) {
        return topicRepository.getAllByProjectId(projectID);
    }

    @Override
    public Topic getTopicByStudent(String projectID, String studentNumber) {
        Topic topic = new Topic();
        topic = topicRepository.findTopicByProjectIdAndStudentListContains(projectID, studentNumber);
        return topic;
    }

    @Override
    public List<User> getUserForTopic(String topicId) {
        Topic topic = topicRepository.findTopicByTopicId(topicId);
        if(topic != null) {
            List<User> studentList = userRepository.findAllByUserNumberIn(topic.getStudentList());
            return studentList;
        }
        return null;
    }

    @Override
    public void registerTopic(String topicId, String userNumber) {
        Topic topic = topicRepository.findTopicByTopicId(topicId);
        User user = userRepository.findByUserNumber(userNumber);
        if(topic.getStudentList() != null) {
            topic.getStudentList().add(userNumber);
        }
        else {
            List<String> studentList = new ArrayList<>();
            studentList.add(userNumber);
            topic.setStudentList(studentList);
        }
        if(user.getTopicList() != null) {
            user.getTopicList().add(topicId);
        }
        else {
            List<String> topicList = new ArrayList<>();
            topicList.add(topicId);
            user.setTopicList(topicList);
        }
        topicRepository.save(topic);
        userRepository.save(user);
    }

    @Override
    public void suggestTopic(TopicDTO topicDTO, String projectId, String userNumber) {
        Project project = projectRepository.findByProjectId(projectId);
        User student = userRepository.findByUserNumber(userNumber);
        String lectureNumber = project.getLectureNumber();
        Notification notification = new Notification();
        notification.setReporter(userNumber);
        notification.setType("suggest topic");
        notification.setStatus(false);
        notification.setReceiver(lectureNumber);
        notification.setStatus(false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedDate = now.format(formatter);
        notification.setTime(formattedDate);

        notification.setMessage(student.getUserName() + " - " + userNumber + " suggest topic: " + topicDTO.getTopicName()
                + " with description: " + topicDTO.getTopicDescription() + " to: " + project.getProjectName() + " - " + project.getClassCode());
        notificationRepository.save(notification);
    }

    @Override
    public void deleteTopic(String topicId) {
        Topic topic = topicRepository.findTopicByTopicId(topicId);
        if(topic != null) {
            topicRepository.delete(topic);
        }
        return;
    }

    @Override
    public void editTopic(String topicId, TopicDTO topicDTO) {
        Topic topic = topicRepository.findTopicByTopicId(topicId);
        if(topic != null) {
            topic.setTopicName(topicDTO.getTopicName());
            topic.setDescription(topicDTO.getTopicDescription());
            topicRepository.save(topic);
        }
        return;
    }

}
