package com.example.kiranafinal.feature_product.service;

import com.example.kiranafinal.feature_product.dto.CreateProductRequest;
import com.example.kiranafinal.feature_product.dto.ProductResponse;
import java.util.List;

/**
 * Service interface for product-related operations.
 */
public interface ProductService {

    /**
     * Adds a new product.
     *
     * @param createProductRequest The details of the product to be added.
     * @return The added product information.
     */
    ProductResponse addProduct(CreateProductRequest createProductRequest);

    /**
     * Updates an existing product.
     *
     * @param productId The ID of the product to update.
     * @param updateProductRequest The updated product details.
     * @return The updated product information.
     */
    ProductResponse updateProduct(String productId, CreateProductRequest updateProductRequest);

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The product details.
     */
    ProductResponse getProductById(String productId);

    /**
     * Retrieves a list of all products.
     *
     * @return A list of all products.
     */
    List<ProductResponse> getAllProducts();
}
