package com.example.database.service.Task;

import com.example.database.entity.Task;

import java.util.List;

public interface TaskService {
    public Task createTask(Task task, String topicId);

    public List<Task> getTaskByStatus(String status, String assignee, String topicId);

    public void changeTaskStatus(String taskId, String newStatus);
}
