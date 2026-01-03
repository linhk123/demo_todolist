package com.uc_modul4.dto; // Nên để ở package event

import com.uc_modul4.entity.Task;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TaskReminderEvent extends ApplicationEvent {
    private final Task task;

    // Sửa: Thêm Task vào tham số truyền vào
    public TaskReminderEvent(Object source, Task task) {
        super(source);
        this.task = task;
    }
}