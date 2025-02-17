package com.example.kiranafinal.feature_user.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_user.dto.UserRequest;
import com.example.kiranafinal.feature_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Register a new user
    @PostMapping("register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.registerUser(request));
        return ResponseEntity.ok(apiResponse);
    }


    // ✅ Get user by ID
    @GetMapping("getUser/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String userId) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<?> user = userService.getUserById(userId);
        user.ifPresent(apiResponse::setData);
        return ResponseEntity.ok(apiResponse);
    }
}
