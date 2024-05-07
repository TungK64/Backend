package com.example.database.service.Topic;

import com.example.database.dto.TopicDTO;
import com.example.database.entity.Topic;
import com.example.database.entity.User;

import java.util.List;


public interface TopicService {
    public Topic createTopic(TopicDTO topicDTO, String projectID);

    public List<Topic> getTopicList(String projectID);

    public Topic getTopicByStudent(String projectID, String studentNumber);

    public List<User> getUserForTopic(String topicId);
}
