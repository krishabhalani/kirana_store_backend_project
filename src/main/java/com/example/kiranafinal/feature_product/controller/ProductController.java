package com.example.kiranafinal.feature_product.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_product.dto.CreateProductRequest;
import com.example.kiranafinal.feature_product.dto.ProductResponse;
import com.example.kiranafinal.feature_product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kiranafinal.feature_product.logConstants.LogConstants.*;

/**
 * Controller for managing products.
 */
@RestController
@RequestMapping("/v1/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Adds a new product.
     * Only accessible by users with ADMIN role.
     *
     * @param createProductRequest The request containing product details.
     * @return ResponseEntity with success or error message.
     */
    @PostMapping("addProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            ProductResponse addedProduct = productService.addProduct(createProductRequest);
            apiResponse.setData(PRODUCT_ADDED + addedProduct.getId());
        } catch (RuntimeException e) {
            apiResponse.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * Updates an existing product.
     *
     * @param productId The ID of the product to update.
     * @param updateProductRequest The request containing updated product details.
     * @return ResponseEntity with success or error message.
     */
    @PutMapping("updateProduct/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable String productId, @RequestBody CreateProductRequest updateProductRequest) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            productService.updateProduct(productId, updateProductRequest);
            apiResponse.setData(PRODUCT_UPDATED + productId);
        } catch (RuntimeException e) {
            apiResponse.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * Retrieves a product by ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return ResponseEntity with product details or error message.
     */
    @GetMapping("getProduct/{productId}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable String productId) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            apiResponse.setData(productService.getProductById(productId));
        } catch (RuntimeException e) {
            apiResponse.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * Lists all products.
     *
     * @return ResponseEntity with the list of all products or an error message if none are available.
     */
    @GetMapping("listAllProducts")
    public ResponseEntity<ApiResponse> listAllProducts() {
        ApiResponse apiResponse = new ApiResponse();
        List<ProductResponse> products = productService.getAllProducts();
        if (products.isEmpty()) {
            apiResponse.setErrorMessage(ERROR_MESSAGE);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        apiResponse.setData(products);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
