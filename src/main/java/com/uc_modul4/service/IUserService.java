package com.uc_modul4.service;

import com.uc_modul4.dto.UserDTO;
import com.uc_modul4.entity.User;

import java.util.List;

public interface IUserService {
    User createUser(User user); // Chỉ Admin
    User updateProfile(Long userId, User updatedUser);
    void deactivateUser(Long userId); // Chuyển status sang INACTIVE thay vì xóa
    User findByUsername(String username);
    UserDTO findByUsernameDTO(String username); // Thêm dòng này
    List<User> findAll();
}
