package com.example.kiranafinal.feature_user.dto;

import com.example.kiranafinal.feature_user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private long phoneNumber; // Changed from address to phoneNumber
}
