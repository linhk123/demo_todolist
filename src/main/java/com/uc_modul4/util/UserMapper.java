package com.uc_modul4.util;

import com.uc_modul4.dto.UserDTO;
import com.uc_modul4.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Từ Entity sang DTO (Dùng khi trả dữ liệu về Client)
    public UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

    // Từ DTO sang Entity (Dùng khi nhận dữ liệu từ Client để lưu vào DB)
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        return user;
    }
}
