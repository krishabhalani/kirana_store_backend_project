package com.example.kiranafinal.feature_user.service;

import com.example.kiranafinal.feature_user.dao.UserDAO;
import com.example.kiranafinal.feature_user.dto.UserRequest;
import com.example.kiranafinal.feature_user.dto.UserResponse;
import com.example.kiranafinal.feature_user.model.User;
import com.example.kiranafinal.feature_user.enums.Role;
import com.example.kiranafinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registers a new user.
     *
     * @param request The user details for registration.
     * @return The registered user information.
     * @throws RuntimeException if the user already exists.
     */
    @Override
    public UserResponse registerUser(UserRequest request) {
        // Check if the user already exists
        if (userDAO.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        // Create a new user
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        user.setPhoneNumber(request.getPhoneNumber());

        // Set the role if provided, otherwise default to USER
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        } else {
            user.setRole(Role.USER);
        }

        user.setCreatedAt(new Date());

        // Save the user
        User savedUser = userDAO.save(user);

        return mapToResponse(savedUser);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return An Optional containing the user information if found.
     */
    @Override
    public Optional<UserResponse> getUserById(String userId) {
        return userDAO.findById(userId).map(this::mapToResponse);
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return A JWT token if authentication is successful.
     * @throws RuntimeException if the user is not found or authentication fails.
     */
    public String authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        // Fetch the user and generate a JWT token
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user.getEmail(), List.of(user.getRole().name()), user.getUserId());
    }

    /**
     * Maps a User entity to a UserResponse DTO.
     *
     * @param user The user entity.
     * @return A UserResponse object with selected user details.
     */
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
