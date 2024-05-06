package com.example.database.service.Topic.impl;

import com.example.database.dto.TopicDTO;
import com.example.database.entity.Topic;
import com.example.database.repository.TopicRepository;
import com.example.database.service.Topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

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
}
