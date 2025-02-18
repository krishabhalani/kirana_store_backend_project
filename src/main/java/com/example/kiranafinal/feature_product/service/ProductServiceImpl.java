package com.example.kiranafinal.feature_product.service;

import com.example.kiranafinal.feature_product.dto.CreateProductRequest;
import com.example.kiranafinal.feature_product.dto.ProductResponse;
import com.example.kiranafinal.feature_product.model.Product;
import com.example.kiranafinal.feature_product.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.kiranafinal.feature_product.logConstants.LogConstants.RUNTIME_EXCEPTION;

/**
 * Service implementation for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Adds a new product.
     *
     * @param createProductRequest The product details.
     * @return The added product details.
     * @throws RuntimeException if a product with the same name already exists.
     */
    @Override
    public ProductResponse addProduct(CreateProductRequest createProductRequest) {
        // Check if a product with the same name already exists
        Optional<Product> existingProduct = productRepository.findByName(createProductRequest.getName());
        if (existingProduct.isPresent()) {
            throw new RuntimeException("Product with name '" + createProductRequest.getName() + "' already exists.");
        }

        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setCategory(createProductRequest.getCategory());
        product.setPrice(createProductRequest.getPrice());
        product.setQuantity(createProductRequest.getQuantity());

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    /**
     * Updates an existing product.
     *
     * @param productId The ID of the product to update.
     * @param updateProductRequest The updated product details.
     * @return The updated product details.
     * @throws RuntimeException if the product is not found.
     */
    @Override
    public ProductResponse updateProduct(String productId, CreateProductRequest updateProductRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(RUNTIME_EXCEPTION + productId));

        product.setName(updateProductRequest.getName());
        product.setCategory(updateProductRequest.getCategory());
        product.setPrice(updateProductRequest.getPrice());
        product.setQuantity(updateProductRequest.getQuantity());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Retrieves a product by ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The product details.
     * @throws RuntimeException if the product is not found.
     */
    @Override
    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(RUNTIME_EXCEPTION + productId));
        return mapToResponse(product);
    }

    /**
     * Retrieves all available products.
     *
     * @return A list of all products.
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Product entity to a ProductResponse DTO.
     *
     * @param product The product entity.
     * @return The product response DTO.
     */
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId().toString(), // Convert ObjectId to String
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
