package com.example.database.service.Task.impl;

import com.example.database.entity.Attachments;
import com.example.database.entity.Notification;
import com.example.database.entity.Task;
import com.example.database.entity.User;
import com.example.database.repository.NotificationRepository;
import com.example.database.repository.TaskRepository;
import com.example.database.repository.UserRepository;
import com.example.database.service.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Task createTask(Map<String, String> task, String topicId, String reporter, String assignee) {
        Task newTask = new Task();
        newTask.setTaskName(task.get("taskName"));
        newTask.setAssignee(assignee);
        if(task.get("deadline") != null) {
            DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDate date = LocalDate.parse(task.get("deadline"), localDateFormatter);
            newTask.setDeadline(date);
        }
        if(task.get("description") != null) {
            newTask.setDescription(task.get("description"));
        }

        newTask.setReporter(reporter);
        newTask.setStart(LocalDate.now());
        newTask.setTopicId(topicId);
        newTask.setPriority("-");
        newTask.setStatus("to-do");

        taskRepository.save(newTask);

        Notification notification = new Notification();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedDate = now.format(formatter);
        notification.setTime(formattedDate);

        notification.setTaskId(newTask.getTaskID());
        notification.setReporter(reporter);
        notification.setType("created");
        User user = userRepository.findByUserNumber(reporter);
        notification.setMessage(user.getUserName() + " created this task");
        notification.setReceiver(assignee);

        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        newTask.setNotifications(notifications);

        notificationRepository.save(notification);
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
            taskRepository.save(task);
        }
        return;
    }

    @Override
    public void addTaskDescription(String taskId, Map<String, String> des) {
        Task task = taskRepository.findOneByTaskID(taskId);
        if(task != null) {
            task.setDescription(des.get("description"));
            taskRepository.save(task);
        }
        return;
    }

    @Override
    public void setPriority(String taskId, String userNumber, String priority) {
        Task task = taskRepository.findOneByTaskID(taskId);
        if(task != null) {
            task.setPriority(priority);
            taskRepository.save(task);

            User user = userRepository.findByUserNumber(userNumber);

            Notification notification = new Notification();
            notification.setTaskId(task.getTaskID());
            notification.setType("notice");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            String formattedDate = now.format(formatter);
            notification.setTime(formattedDate);
            notification.setReporter(userNumber);
            notification.setMessage(user.getUserName() + " changed priority to " + priority);
            notificationRepository.save(notification);

            task.getNotifications().add(notification);
            taskRepository.save(task);
        }
        return;
    }

    @Override
    public void addAttachments(List<Attachments> attachList, String taskId, String userNumber, String receiver) {
        Task task = taskRepository.findOneByTaskID(taskId);
        if(task != null) {
            task.setAttachments(attachList);
            taskRepository.save(task);

            User user = userRepository.findByUserNumber(userNumber);
            Notification notification = new Notification();
            notification.setTaskId(task.getTaskID());
            notification.setType("notice");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            String formattedDate = now.format(formatter);
            notification.setTime(formattedDate);
            notification.setReporter(userNumber);
            notification.setReceiver(receiver);
            notification.setStatus(false);
            notification.setMessage(user.getUserName() + " added attachments to " + task.getTaskName());
            notificationRepository.save(notification);

            task.getNotifications().add(notification);
            taskRepository.save(task);
        }
        return;
    }

    @Override
    public void addComment(String comment, String taskId, String userNumber, String receiver) {
        Task task = taskRepository.findOneByTaskID(taskId);
        if(task != null) {
            User user = userRepository.findByUserNumber(userNumber);
            Notification notification = new Notification();
            notification.setTaskId(task.getTaskID());
            notification.setType("comment");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            String formattedDate = now.format(formatter);
            notification.setTime(formattedDate);
            notification.setReporter(userNumber);
            notification.setReceiver(receiver);
            notification.setStatus(false);
            notification.setMessage(user.getUserName() + " comment " + comment + " to " + task.getTaskName());
            notificationRepository.save(notification);

            task.getNotifications().add(notification);
            taskRepository.save(task);
        }
        return;
    }

    @Override
    public void changeDeadline(String taskId, String userNumber, String receiver, Map<String, String> newDeadline) {
        Task task = taskRepository.findOneByTaskID(taskId);
        if(task != null) {
            DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            // Parse the string to LocalDate
            LocalDate date = LocalDate.parse(newDeadline.get("deadline"), localDateFormatter);

            User user = userRepository.findByUserNumber(userNumber);
            Notification notification = new Notification();
            notification.setTaskId(task.getTaskID());
            notification.setType("notice");
            notification.setReporter(userNumber);
            notification.setReceiver(receiver);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            String formattedDate = now.format(formatter);
            notification.setTime(formattedDate);

            notification.setMessage(user.getUserName() + " changed deadline to " + date);
            notificationRepository.save(notification);

            task.getNotifications().add(notification);
            task.setDeadline(date);
            taskRepository.save(task);
        }
    }
}
