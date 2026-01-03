package com.uc_modul4.dto;

import java.time.LocalDateTime;

public class ReminderRequest {
    private LocalDateTime reminderTime;

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }
}
