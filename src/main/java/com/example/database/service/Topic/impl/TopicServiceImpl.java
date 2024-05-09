package com.example.database.service.Topic.impl;

import com.example.database.dto.TopicDTO;
import com.example.database.entity.Topic;
import com.example.database.entity.User;
import com.example.database.repository.TopicRepository;
import com.example.database.repository.UserRepository;
import com.example.database.service.Topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;

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
            System.out.println(studentList);
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


}
