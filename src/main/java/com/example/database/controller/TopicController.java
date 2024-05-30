package com.example.database.controller;

import com.example.database.dto.ProjectDTO;
import com.example.database.dto.TopicDTO;
import com.example.database.entity.Project;
import com.example.database.entity.Topic;
import com.example.database.entity.User;
import com.example.database.repository.TopicRepository;
import com.example.database.service.Topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    private TopicRepository topicRepository;

    @PostMapping("/create-topic/{projectID}")
    public ResponseEntity<?> createTopic(@RequestBody TopicDTO topicDTO, @PathVariable String projectID) {
        Topic topic = topicService.createTopic(topicDTO, projectID);
        if (topic == null) {
            return new ResponseEntity<>("Topic created faile", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Topic created success", HttpStatus.CREATED);
    }

    @GetMapping("/get-topic/{projectID}")
    public ResponseEntity<?> getTopicList(@PathVariable String projectID) {
        List<Topic> topicList = new ArrayList<>();
        topicList = topicService.getTopicList(projectID);
        if (topicList == null) {
            return new ResponseEntity<>("No topic found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(topicList, HttpStatus.OK);
        }
    }

    @GetMapping("/get-topic-by-student/{projectId}/{studentNumber}")
    public ResponseEntity<?> getTopicByStudent(@PathVariable String projectId, @PathVariable String studentNumber) {
        Topic topic = topicService.getTopicByStudent(projectId, studentNumber);
        if (topic == null) {return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);}
        else {
            return new ResponseEntity<>(topic, HttpStatus.OK);
        }
    }

    @GetMapping("/get-student-in-topic/{topicId}")
    public ResponseEntity<?> getStudentInTopic(@PathVariable String topicId) {
        List<User> studentList = topicService.getUserForTopic(topicId);
        if (studentList == null) {return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);}
        else { return new ResponseEntity<>(studentList, HttpStatus.OK); }
    }

    @PostMapping("/register-topic/{topicId}/{userNumber}")
    public ResponseEntity<?> registerTopic(@PathVariable String topicId, @PathVariable String userNumber) {
        topicService.registerTopic(topicId, userNumber);
        return new ResponseEntity<>("Topic registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-topic-by-id/{topicId}")
    public ResponseEntity<?> getTopic(@PathVariable String topicId) {
        Topic topic = topicRepository.findTopicByTopicId(topicId);
        if(topic == null) {return new ResponseEntity<>("No topic found", HttpStatus.NOT_FOUND);}
        else {
            return new ResponseEntity<>(topic, HttpStatus.OK);
        }
    }

    @PostMapping("/suggest/{userNumber}/{projectId}")
    public ResponseEntity<?> suggestTopic(@RequestBody TopicDTO topicInfo,@PathVariable String projectId, @PathVariable String userNumber) {
        topicService.suggestTopic(topicInfo, projectId, userNumber);
        return new ResponseEntity<>("Suggest topic successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable String topicId) {
        topicService.deleteTopic(topicId);
        return new ResponseEntity<>("Topic deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/edit-topic/{topicId}")
    public ResponseEntity<?> editTopic(@RequestBody TopicDTO topicDTO, @PathVariable String topicId) {
        topicService.editTopic(topicId, topicDTO);
        return new ResponseEntity<>("Topic edited successfully", HttpStatus.OK);
    }
}
