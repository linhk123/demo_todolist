package com.uc_modul4.event;

import com.uc_modul4.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TaskEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishTaskReminder(Task task) {
        TaskReminderEvent event = new TaskReminderEvent(this, task);
        eventPublisher.publishEvent(event);
    }
}