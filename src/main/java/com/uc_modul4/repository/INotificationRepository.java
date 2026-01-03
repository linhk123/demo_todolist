package com.uc_modul4.repository;

import com.uc_modul4.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    // Lấy thông báo chưa đọc của 1 user
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);
}