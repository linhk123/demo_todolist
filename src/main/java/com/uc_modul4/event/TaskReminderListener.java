package com.uc_modul4.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskReminderListener {
    @Async
    @EventListener
    public void handleTaskReminder(TaskReminderEvent event) {
        System.out.println("--- NHẮC NHỞ CÔNG VIỆC ---");
        System.out.println("Tên task: " + event.getTask().getTitle());
        System.out.println("Nội dung: " + event.getTask().getDescription());
        // Bạn có thể gọi EmailService hoặc NotificationService ở đây
    }
}