package com.example.kiranafinal.feature_user.repo;

import com.example.kiranafinal.feature_user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * Repository interface for managing { User} entities in MongoDB.
 * Extends {@link MongoRepository} to provide built-in CRUD operations.
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email of the user to find
     * @return an { Optional} containing the user if found, otherwise empty
     */
    Optional<User> findByEmail(String email);
}
