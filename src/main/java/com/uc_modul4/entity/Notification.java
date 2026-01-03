package com.uc_modul4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data // Tự động tạo getter, setter, equals, hashCode và toString
@NoArgsConstructor // Tạo constructor không tham số
@AllArgsConstructor // Tạo constructor đầy đủ tham số
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // VD: TASK_ASSIGNED, DEADLINE_REMINDER

    @Column(columnDefinition = "TEXT")
    private String message;

    private boolean isRead = false;

    // Receiver: Một người dùng có thể nhận nhiều thông báo
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    // Tự động gán thời gian tạo khi lưu vào database
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

