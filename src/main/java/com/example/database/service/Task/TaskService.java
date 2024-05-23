package com.example.database.service.Task;

import com.example.database.entity.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {
    public Task createTask(Task task, String topicId, String reporter, String assignee);

    public List<Task> getTaskByStatus(String status, String assignee, String topicId);

    public void changeTaskStatus(String taskId, String newStatus);

    public void addTaskDescription(String taskId, Map<String, String> des);

    void setPriority(String taskId, String priority, String userNumber);

    void addAttachments(List<byte[]> attachList, String taskId, String userNumber, String fileName, String receiver);

    void addComment(String comment, String taskId, String userNumber, String receiver);

    void changeDeadline(String taskId, String userNumber, String receiver, Map<String, String> newDeadline);
}
