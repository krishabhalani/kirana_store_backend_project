package com.example.kiranafinal.feature_product.repo;

import com.example.kiranafinal.feature_product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {  // ✅ Use String as ID
    Optional<Product> findByName(String name); // ✅ Custom query to find a product by name
}
