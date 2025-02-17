package com.example.kiranafinal.feature_user.model;

import com.example.kiranafinal.feature_user.model.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Getter
@Setter
@Document(collection = "users") // MongoDB collection name
public class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private long phoneNumber;  // ✅ Use long instead of int
    private Role role;
    private Instant createdAt = Instant.now();
}
