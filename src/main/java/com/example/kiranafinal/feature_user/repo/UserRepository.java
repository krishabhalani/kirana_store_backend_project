package com.example.kiranafinal.feature_user.repo;

import com.example.kiranafinal.feature_user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
