package com.uc_modul4.repository;

import com.uc_modul4.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    // Đếm số lượng task theo trạng thái trong một dự án (Dùng cho ReportService)
    long countByProjectIdAndStatus(Long projectId, Task.TaskStatus status);

    // Tìm task hoàn thành của một user trong khoảng thời gian (Dùng cho Productivity Report)
    List<Task> findAllByAssignedToIdAndStatusAndDueDateBetween(
            Long userId, Task.TaskStatus status, LocalDate start, LocalDate end);

    // Tìm các task sắp đến hạn nhưng chưa xong
    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, Task.TaskStatus status);
}
