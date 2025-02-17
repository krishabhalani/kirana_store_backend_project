package com.example.kiranafinal.feature_user.service;

import com.example.kiranafinal.feature_user.dao.UserDAO;
import com.example.kiranafinal.feature_user.dto.UserRequest;
import com.example.kiranafinal.feature_user.dto.UserResponse;
import com.example.kiranafinal.feature_user.model.User;
import com.example.kiranafinal.feature_user.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserResponse registerUser(UserRequest request) {
        //  Check if user already exists
        if (userDAO.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        // Create new user
        User user = new User();
        user.setUserId(UUID.randomUUID().toString()); // Generate unique ID
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber()); // ✅ Set phone number
        user.setRole(Role.USER); // Default role: USER
        user.setCreatedAt(Instant.now());

        // ✅ Save User
        User savedUser = userDAO.save(user);

        return mapToResponse(savedUser);
    }


    @Override
    public Optional<UserResponse> getUserById(String userId) {
        return userDAO.findById(userId).map(this::mapToResponse);
    }

    // ✅ Updated mapToResponse method to return only required fields
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber() // ✅ Updated response to include phoneNumber
        );
    }
}
