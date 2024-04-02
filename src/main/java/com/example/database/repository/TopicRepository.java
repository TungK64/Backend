package com.example.database.repository;

import com.example.database.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<Topic, Integer> {
}
