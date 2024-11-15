package com.example.database.service.Topic;

import com.example.database.dto.TopicDTO;
import com.example.database.entity.Topic;
import com.example.database.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {
    public Topic createTopic(TopicDTO topicDTO, String projectID);

    public List<Topic> getTopicList(String projectID);

    public Topic getTopicByStudent(String projectID, String studentNumber);

    public List<User> getUserForTopic(String topicId);

    public void registerTopic(String topicId, String userNumber);

    void suggestTopic(TopicDTO topicDTO, String projectId, String userNumber);

    void deleteTopic(String topicId);

    void editTopic(String topicId, TopicDTO topicDTO);
}
