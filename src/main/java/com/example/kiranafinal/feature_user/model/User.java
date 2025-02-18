package com.example.kiranafinal.feature_user.model;

import com.example.kiranafinal.feature_user.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Represents a user entity in the system.
 * This class is mapped to the "users" collection in MongoDB.
 */
@Getter
@Setter
@Document(collection = "users") // MongoDB collection name
public class User {

    /**
     * Unique identifier for the user.
     * This field is the primary key in the MongoDB collection.
     */
    @Id
    private String userId;

    /**
     * Full name of the user.
     */
    private String name;

    /**
     * Email address of the user.
     * This field should be unique for each user.
     */
    private String email;

    /**
     * Hashed password of the user.
     * This field should be securely stored and never exposed.
     */
    private String password;

    /**
     * Contact phone number of the user.
     * Using long data type to accommodate 10+ digit numbers.
     */
    private long phoneNumber;

    /**
     * Role of the user in the system (e.g., ADMIN, CUSTOMER).
     * This is typically used for authorization.
     */
    private Role role;

    /**
     * Timestamp indicating when the user was created.
     * Defaults to the current system date at the moment of creation.
     */
    private Date createdAt = new Date();
}
