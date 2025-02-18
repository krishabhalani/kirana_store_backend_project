package com.example.kiranafinal.feature_user.service;

import com.example.kiranafinal.feature_user.dto.UserRequest;
import com.example.kiranafinal.feature_user.dto.UserResponse;

import java.util.Optional;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Registers a new user.
     *
     * @param request The user details for registration.
     * @return The registered user information.
     */
    UserResponse registerUser(UserRequest request);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return An Optional containing the user information if found.
     */
    Optional<UserResponse> getUserById(String userId);
}
