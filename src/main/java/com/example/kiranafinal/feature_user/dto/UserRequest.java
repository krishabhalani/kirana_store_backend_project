package com.example.kiranafinal.feature_user.dto;

import com.example.kiranafinal.feature_user.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private long phoneNumber;  // ✅ Use long instead of int
    private Role role;
}
