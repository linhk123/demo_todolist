package com.uc_modul4.service.Service;

import com.uc_modul4.entity.Notification;
import com.uc_modul4.entity.User;
import com.uc_modul4.repository.INotificationRepository;
import com.uc_modul4.repository.IUserRepository;
import com.uc_modul4.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private INotificationRepository notificationRepository;
    @Autowired private JavaMailSender mailSender;
    @Autowired private  IUserRepository userRepository;
    public NotificationService(IUserRepository userRepository,
                                   INotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }
    @Override
    public void sendInApp(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification note = new Notification();
        note.setUser(user);
        note.setMessage(message);
        note.setType("SYSTEM");
        note.setCreatedAt(LocalDateTime.now()); // Đảm bảo có thời gian tạo
        notificationRepository.save(note);
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setSubject(subject);
        mail.setText(body);
        mailSender.send(mail);
    }

    @Override
    public void sendPush(Long userId, String title, String body) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lưu notification vào DB
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType("PUSH");
        notification.setMessage(title + " - " + body);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        // Giả lập gửi push (Firebase / OneSignal sau này)
        System.out.println("📲 Push sent to user " + user.getEmail()
                + ": " + title + " | " + body);
    }

}
