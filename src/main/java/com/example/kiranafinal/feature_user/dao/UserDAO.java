package com.example.kiranafinal.feature_user.dao;

import com.example.kiranafinal.feature_user.model.User;
import com.example.kiranafinal.feature_user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    // Save new user
    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);  // Use String, NOT ObjectId
    }


    // Get user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Delete user
    public void deleteById(String userId) {
        userRepository.deleteById(userId);
    }
}
