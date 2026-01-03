package com.uc_modul4.controller;

import com.uc_modul4.dto.UserDTO;
import com.uc_modul4.entity.User;
import com.uc_modul4.service.IUserService;
import com.uc_modul4.service.Service.UserService; // Import để dùng toDTO
import com.uc_modul4.util.ResponseHandler;
import com.uc_modul4.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    // Lấy thông tin người dùng đang đăng nhập
    @GetMapping("/me")
    public ResponseEntity<Object> getMyProfile() {
        String username = SecurityUtils.getCurrentUsername();

        // 1. Lấy Entity từ Service
        User user = userService.findByUsername(username);

        // 2. Ép kiểu sang UserService (impl) để gọi method toDTO
        // Hoặc tốt nhất là khai báo toDTO trong Interface IUserService
        UserDTO userDTO = ((UserService) userService).toDTO(user);

        return ResponseHandler.generateResponse("Lấy thông tin thành công", HttpStatus.OK, userDTO);
    }

    // Cập nhật hồ sơ
    @PutMapping("/profile")
    public ResponseEntity<Object> updateProfile(@RequestBody User updatedUser) {
        // Lấy ID từ token để đảm bảo an toàn (không dùng ID từ RequestBody)
        // Giả sử bạn bổ sung method getUserId vào SecurityUtils
        // Long currentUserId = SecurityUtils.getCurrentUserId();

        // Tạm thời minh họa:
        User user = userService.updateProfile(1L, updatedUser);
        UserDTO userDTO = ((UserService) userService).toDTO(user);

        return ResponseHandler.generateResponse("Cập nhật hồ sơ thành công", HttpStatus.OK, userDTO);
    }

    // Vô hiệu hóa tài khoản (Soft Delete)
    @DeleteMapping("/{id}/deactivate")
    public ResponseEntity<Object> deactivate(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseHandler.generateResponse("Đã vô hiệu hóa người dùng", HttpStatus.OK, null);
    }
}