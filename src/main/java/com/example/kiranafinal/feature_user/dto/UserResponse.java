package com.example.kiranafinal.feature_user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for returning user details in responses.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private long phoneNumber; // Changed from address to phoneNumber
}
