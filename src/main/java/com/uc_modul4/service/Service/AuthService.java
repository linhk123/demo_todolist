package com.uc_modul4.service.Service;

import com.uc_modul4.entity.User;
import com.uc_modul4.repository.IUserRepository;
import com.uc_modul4.security.JwtProvider;
import com.uc_modul4.service.IAuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AuthService implements IAuthService {
    @Autowired private IUserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtProvider jwtProvider;

    @Override
    public User register(User user) {
        // 1. Kiểm tra username tồn tại
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Lỗi: Tên đăng nhập đã tồn tại!");
        }

        // 2. Kiểm tra email tồn tại
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Lỗi: Email đã được sử dụng!");
        }

        // 3. Mã hóa mật khẩu (CHỈ MỘT LẦN DUY NHẤT)
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 4. Thiết lập giá trị mặc định
        user.setRole("ROLE_USER");
        user.setStatus("ACTIVE");

        // 5. Lưu vào DB
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        // 1. Tìm user (trả về cái "hộp" Optional)
        Optional<User> userOpt = userRepository.findByUsername(username);

        // 2. Kiểm tra xem trong "hộp" có User không
        if (userOpt.isPresent()) {
            User user = userOpt.get(); // Lấy User thật ra

            // 3. So khớp mật khẩu đã mã hóa
            if (passwordEncoder.matches(password, user.getPassword())) {

                // 4. Kiểm tra trạng thái
                if ("INACTIVE".equals(user.getStatus())) {
                    throw new RuntimeException("Tài khoản đã bị khóa!");
                }
                return user; // Đăng nhập thành công!
            }
        }

        // 5. Nếu không vào được if ở trên thì báo lỗi
        throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác!");
    }

    @Override
    public void logout(String token) {
        // Xóa SecurityContext
        SecurityContextHolder.clearContext();
        // Nếu có Redis, bạn sẽ thêm token vào blacklist tại đây
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtProvider.generateRefreshToken(oldToken);
    }
}