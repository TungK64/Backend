package com.example.database.service.Task;

import com.example.database.entity.Attachments;
import com.example.database.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskService {
    public Task createTask(Map<String, String> task, String topicId, String reporter, String assignee);

    public List<Task> getTaskByStatus(String status, String assignee, String topicId);

    public void changeTaskStatus(String taskId, String newStatus);

    public void addTaskDescription(String taskId, Map<String, String> des);

    void setPriority(String taskId, String priority, String userNumber);

    void addAttachments(List<Attachments> attachList, String taskId, String userNumber, String receiver);

    void addComment(String comment, String taskId, String userNumber, String receiver);

    void changeDeadline(String taskId, String userNumber, String receiver, Map<String, String> newDeadline);
}
