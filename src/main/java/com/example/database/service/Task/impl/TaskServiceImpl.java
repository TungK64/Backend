package com.example.database.service.Task.impl;

import com.example.database.entity.Task;
import com.example.database.repository.TaskRepository;
import com.example.database.service.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String topicId) {
        Task newTask = new Task();
        newTask.setTaskName(task.getTaskName());
        newTask.setAssignee(task.getAssignee());
        newTask.setDeadline(task.getDeadline());
        newTask.setDescription(task.getDescription());
        newTask.setReporter(task.getReporter());
        newTask.setStart(LocalDate.now());
        newTask.setTopicId(topicId);
        newTask.setStatus("to-do");

        taskRepository.save(newTask);
        return newTask;
    }

    @Override
    public List<Task> getTaskByStatus(String status, String assignee, String topicId) {
        List<Task> taskList = new ArrayList<>();
        taskList = taskRepository.findAllByStatusAndAssigneeAndTopicId(status, assignee, topicId);
        return taskList;
    }

    @Override
    public void changeTaskStatus(String taskId, String newStatus) {
        Task task = taskRepository.findOneByTaskID(taskId);
        if(task != null) {
            task.setStatus(newStatus);
//            ZoneId vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
//            ZonedDateTime vietnamTime = ZonedDateTime.now(vietnamZoneId);
//            task.setUpdate(vietnamTime);
            taskRepository.save(task);
        }
        return;
    }
}
