package com.example.kiranafinal.feature_product.service;

import com.example.kiranafinal.feature_product.dto.CreateProductRequest;
import com.example.kiranafinal.feature_product.dto.ProductResponse;
import java.util.List;

public interface ProductService {
    ProductResponse addProduct(CreateProductRequest createProductRequest);
    ProductResponse updateProduct(String productId, CreateProductRequest updateProductRequest);
    ProductResponse getProductById(String productId);
    List<ProductResponse> getAllProducts();
}