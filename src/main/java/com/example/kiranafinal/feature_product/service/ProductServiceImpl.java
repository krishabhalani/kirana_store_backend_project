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

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Adds a new product. Throws an exception if the product already exists.
     */
    @Override
    public ProductResponse addProduct(CreateProductRequest createProductRequest) {
        //  Check if a product with the same name already exists
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
     * Updates an existing product. Throws an exception if the product is not found.
     */
    @Override
    public ProductResponse updateProduct(String productId, CreateProductRequest updateProductRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        product.setName(updateProductRequest.getName());
        product.setCategory(updateProductRequest.getCategory());
        product.setPrice(updateProductRequest.getPrice());
        product.setQuantity(updateProductRequest.getQuantity());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     *  Retrieves a product by ID. Throws an exception if the product is not found.
     */
    @Override
    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        return mapToResponse(product);
    }

    /**
     *  Returns all available products.
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Utility method to map Product to ProductResponse (Prevents repetition).
     */
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId().toString(), // ✅ Convert ObjectId to String
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
