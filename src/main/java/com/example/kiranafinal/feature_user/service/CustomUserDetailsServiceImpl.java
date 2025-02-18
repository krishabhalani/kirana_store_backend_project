package com.example.kiranafinal.feature_user.service;

import com.example.kiranafinal.feature_user.dao.UserDAO;
import com.example.kiranafinal.feature_user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * Service for loading user details for authentication.
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;

    /**
     * Constructor to initialize UserDAO.
     */
    @Autowired
    public CustomUserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Loads user details by email for authentication.
     *
     * @param email The user's email.
     * @return UserDetails object with user credentials.
     * @throws UsernameNotFoundException if user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Use UserBuilder to create a Spring Security UserDetails object
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
        builder.password(user.getPassword());  // Set hashed password
        builder.roles(user.getRole().name());  // Convert enum role to String

        return builder.build();  // Return UserDetails
    }
}
