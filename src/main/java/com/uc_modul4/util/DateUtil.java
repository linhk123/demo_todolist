package com.uc_modul4.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class DateUtil {
    private static final String DEFAULT_FORMAT = "dd/MM/yyyy HH:mm:ss";

    // Chuyển đổi LocalDateTime thành String để trả về API
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
    }

    // Kiểm tra xem thời gian nhắc nhở đã đến chưa
    public static boolean isTimeToSendReminder(LocalDateTime reminderTime) {
        if (reminderTime == null) return false;
        LocalDateTime now = LocalDateTime.now();
        // Cho phép sai số trong khoảng 1 phút
        return Duration.between(now, reminderTime).toMinutes() == 0;
    }
}