package com.example.kiranafinal.feature_product.repo;

import com.example.kiranafinal.feature_product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing product-related database operations.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    /**
     * Finds a product by its name.
     *
     * @param name The name of the product.
     * @return An Optional containing the product if found.
     */
    Optional<Product> findByName(String name);
}
