package com.example.database.repository;

import com.example.database.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    List<Topic> getAllByProjectId(String projectID);

    Topic findTopicByProjectIdAndStudentListContains(String projectID, String studentNumber);
}
