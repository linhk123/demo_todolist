package com.uc_modul4.service;

import com.uc_modul4.entity.User;

public interface IAuthService {
    User login(String username, String password);
    User register(User user);
    void logout(String token);
    String refreshToken(String oldToken);
}
