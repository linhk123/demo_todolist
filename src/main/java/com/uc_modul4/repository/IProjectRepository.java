package com.uc_modul4.repository;

import com.uc_modul4.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {
    // Lấy danh sách dự án mà một người làm chủ sở hữu
    List<Project> findByOwnerId(Long ownerId);

    // Tìm kiếm dự án theo tên
    List<Project> findByNameContainingIgnoreCase(String name);
}
