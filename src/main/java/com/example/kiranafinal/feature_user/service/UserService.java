package com.example.kiranafinal.feature_user.service;

import com.example.kiranafinal.feature_user.dto.UserRequest;
import com.example.kiranafinal.feature_user.dto.UserResponse;

import java.util.Optional;

public interface UserService {
    UserResponse registerUser(UserRequest request);
    Optional<UserResponse> getUserById(String userId);
}
