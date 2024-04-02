package com.example.database.repository;

import com.example.database.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
}
