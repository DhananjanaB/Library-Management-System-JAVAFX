package service;

import repository.UserRepository;

import java.sql.SQLException;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public boolean login(String username, String password) throws SQLException {
        if (username.trim().isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password are required");
        }
        return userRepository.checkLogin(username.trim(), password);
    }
}