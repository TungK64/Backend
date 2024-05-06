package com.example.database.repository;

import com.example.database.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByClassCodeIn(List<Integer> code);

    Project findByProjectNameAndClassCode(String name, int code);

    Project findByProjectId(String projectId);
}
