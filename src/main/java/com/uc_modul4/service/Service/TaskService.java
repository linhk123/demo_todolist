package com.uc_modul4.service.Service;

import com.uc_modul4.event.TaskReminderEvent;
import com.uc_modul4.entity.Task;
import com.uc_modul4.entity.User;
import com.uc_modul4.repository.ITaskRepository;
import com.uc_modul4.repository.IUserRepository;
import com.uc_modul4.service.INotificationService;
import com.uc_modul4.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TaskService implements ITaskService {
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired private INotificationService notificationService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Task createTask(Task task, Long projectId) {
        // Gán Project cho Task trước khi lưu
        return taskRepository.save(task);
    }

    @Override
    public Task assignTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        task.setAssignedTo(user);        task.setAssignedTo(user);
        // Thông báo cho người dùng
        notificationService.sendInApp(userId, "Bạn có nhiệm vụ mới: " + task.getTitle());
        return taskRepository.save(task);
    }

    @Override
    public Task updateStatus(Long taskId, Task.TaskStatus status) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public void setReminder(Long taskId, LocalDateTime reminderTime) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setReminderTime(reminderTime);
        taskRepository.save(task);

        // Publish event
        eventPublisher.publishEvent(new TaskReminderEvent(this, task));
    }

    @Override
    public List<Task> findByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}