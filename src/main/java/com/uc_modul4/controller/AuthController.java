package com.uc_modul4.controller;

import com.uc_modul4.entity.User;
import com.uc_modul4.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private IUserService userService; // Sử dụng Interface để gọi logic từ UserService

    @GetMapping("/")
    public String index() {
        // Khi vào trang chủ, nếu chưa login Spring Security sẽ tự đá về /login
        return "redirect:/tasks/kanban";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            // Sử dụng hàm createUser đã có logic mã hóa mật khẩu, check tồn tại, gán ROLE
            userService.createUser(user);

            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Hãy đăng nhập.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            // Bắt lỗi "Tên đăng nhập đã tồn tại" từ Service và gửi về giao diện
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi hệ thống xảy ra!");
            return "redirect:/register";
        }
    }
    @GetMapping("/home")
    public String homeRedirect() {
        return "redirect:/tasks/kanban";
    }
}