package com.example.kiranafinal.feature_user.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_user.dto.UserRequest;
import com.example.kiranafinal.feature_user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling user-related operations.
 */
@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Registers a new user.
     *
     * @param request The user details for registration.
     * @return ResponseEntity containing the registered user details.
     */
    @PostMapping("register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.registerUser(request));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing the user details if found.
     */
    @GetMapping("getUser/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String userId) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<?> user = userService.getUserById(userId);
        user.ifPresent(apiResponse::setData);
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request The user login credentials.
     * @return ResponseEntity containing the JWT token.
     */
    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserRequest request) {
        String token = userService.authenticateUser(request.getEmail(), request.getPassword());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(Map.of("token", token)); // Return JWT token
        return ResponseEntity.ok(apiResponse);
    }
}
