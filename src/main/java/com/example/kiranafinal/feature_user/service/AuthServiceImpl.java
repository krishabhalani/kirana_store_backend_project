package com.example.kiranafinal.feature_user.service;

import com.example.kiranafinal.feature_user.dao.UserDAO;
import com.example.kiranafinal.feature_user.model.User;
import com.example.kiranafinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for user authentication.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor to initialize dependencies.
     */
    @Autowired
    public AuthServiceImpl(JwtUtil jwtUtil, UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param email    User's email.
     * @param password User's password.
     * @return JWT token if authentication is successful.
     * @throws RuntimeException if user is not found or credentials are incorrect.
     */
    @Override
    public String authenticate(String email, String password) {
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), List.of(user.getRole().name()), user.getUserId());
    }
}
