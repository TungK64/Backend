package com.example.database.repository;

import com.example.database.entity.Project;
import com.example.database.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {
    List<User> findAllByRole(String role);

    User findByRoleAndProjectListContaining(String role, int code);

    List<User> findAllByRoleAndProjectListContaining(String role, List<Integer> classCode);

    List<User> findAllByRoleAndProjectListContains(String role, int code);

    User findByUserNumberAndRole(String number, String role);

    User findByEmail(String email);

    User findByUserNumber(String number);

}
