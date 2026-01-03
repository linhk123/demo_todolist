package com.uc_modul4.service;

public interface INotificationService {
    void sendInApp(Long userId, String message); // Lưu vào bảng Notification
    void sendEmail(String toEmail, String subject, String body);
    void sendPush(Long userId, String title, String body); // Dùng Firebase/WebPush
}
