package com.example.database.repository;

import com.example.database.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findAllByStatus(String status);

    List<Task> findAllByStatusAndAssigneeAndTopicId(String status, String assignee, String topicId);

    Task findOneByTaskID(String id);
}
