package com.uc_modul4;

import com.uc_modul4.entity.Type;
import com.uc_modul4.entity.User;
import com.uc_modul4.repository.ITypeRepository;
import com.uc_modul4.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableAsync
@SpringBootApplication
public class UcModul4Application {

    public static void main(String[] args) {
        SpringApplication.run(UcModul4Application.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner dataLoader(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                if (userRepository.count() == 0) {
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPassword(passwordEncoder.encode("123456"));
                    admin.setEmail("admin@gmail.com");
                    admin.setRole("ROLE_ADMIN");
                    admin.setStatus("ACTIVE");
                    // Thêm các trường bắt buộc khác nếu Entity của bạn có (ví dụ: fullName, phone...)

                    userRepository.save(admin);
                    System.out.println(">>> Đã nạp User mẫu thành công!");
                }
            } catch (Exception e) {
                System.err.println(">>> Lỗi nạp dữ liệu User: " + e.getMessage());
                // Không để lỗi này làm sập ứng dụng
            }
        };
    }

    @Bean
    public CommandLineRunner typeLoader(ITypeRepository typeRepository) {
        return args -> {
            if (typeRepository.count() == 0) {
                Type bug = new Type();
                bug.setName("BUG");
                typeRepository.save(bug);

                Type feature = new Type();
                feature.setName("FEATURE");
                typeRepository.save(feature);

                Type task = new Type();
                task.setName("TASK");
                typeRepository.save(task);

                System.out.println(">>> Đã nạp dữ liệu Type thành công!");
            }
        };
    }

}
