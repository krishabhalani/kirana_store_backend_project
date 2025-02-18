package com.example.kiranafinal.feature_user.dto;

import com.example.kiranafinal.feature_user.enums.Role;
import lombok.Getter;
import lombok.Setter;


/**
 * DTO for user registration requests.
 */
@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private String password;  // ✅ Add password field
    private long phoneNumber;  // ✅ Use long instead of int
    private Role role;
}
