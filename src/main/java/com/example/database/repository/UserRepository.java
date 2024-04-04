package com.example.database.repository;

import com.example.database.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {
    List<User> findAllByRole(String role);
}
