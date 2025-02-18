package com.example.kiranafinal.feature_user.dao;

import com.example.kiranafinal.feature_user.model.User;
import com.example.kiranafinal.feature_user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data Access Object (DAO) for user-related database operations.
 */
@Repository
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a new user in the database.
     *
     * @param user The user to be saved.
     * @return The saved user entity.
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user.
     * @return An Optional containing the user if found.
     */
    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user.
     * @return An Optional containing the user if found.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
