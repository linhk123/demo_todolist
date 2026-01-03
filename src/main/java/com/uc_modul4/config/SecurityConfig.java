package com.uc_modul4.config;

import com.uc_modul4.entity.User;
import com.uc_modul4.repository.IUserRepository;
import com.uc_modul4.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Rất quan trọng, nếu không sẽ bị reset trang liên tục
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // Nơi nhận dữ liệu từ Form
                        .defaultSuccessUrl("/tasks/kanban", true) // ĐÍCH ĐẾN KHI THÀNH CÔNG
                        .failureUrl("/login?error=true") // QUAY LẠI KHI THẤT BẠI
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/login"));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(IUserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .map(user -> {
                    // Kiểm tra nếu role bị null thì gán mặc định là ROLE_USER
                    String roleName = (user.getRole() != null) ? user.getRole() : "ROLE_USER";

                    // Đảm bảo có tiền tố ROLE_
                    if (!roleName.startsWith("ROLE_")) {
                        roleName = "ROLE_" + roleName;
                    }

                    return org.springframework.security.core.userdetails.User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .authorities(roleName)
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}