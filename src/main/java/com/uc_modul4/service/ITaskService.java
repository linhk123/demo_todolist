package com.uc_modul4.service;

import com.uc_modul4.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface ITaskService {
    Task createTask(Task task, Long projectId);
    Task assignTask(Long taskId, Long userId);
    Task updateStatus(Long taskId, Task.TaskStatus status);
    void setReminder(Long taskId, LocalDateTime reminderTime);
    List<Task> findByProject(Long projectId);
    List<Task> findAll();
}
