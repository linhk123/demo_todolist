package com.uc_modul4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Tên không được để trống!")
    @Size(min = 1, max = 20, message= "Tên phải có 1 đến 20 kí tự! ")
    @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$")
    @Column(unique = true, nullable = false)
    private String username;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng, VD:example@gmail.com")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 255) // Tăng max lên 255 vì mật khẩu sau mã hóa rất dài
// @Pattern(regexp = "...")  <-- QUAN TRỌNG: Hãy XÓA hoặc COMMENT dòng @Pattern này lại
    @Column(name = "password", nullable = false)
    private String password;    //@Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\"\n",

    //Role (vai trò) xác định người dùng được phép làm gì trong hệ thống.
    private String role;

    //Status (trạng thái) cho biết tài khoản đang ở tình trạng nào.
    private String status;

    public User() {}

    public User(Long id, String username, String email, String password, String role, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
