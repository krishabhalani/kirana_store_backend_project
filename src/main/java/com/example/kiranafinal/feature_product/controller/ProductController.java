package com.example.kiranafinal.feature_product.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_product.dto.CreateProductRequest;
import com.example.kiranafinal.feature_product.dto.ProductResponse;
import com.example.kiranafinal.feature_product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("addProduct")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            ProductResponse addedProduct = productService.addProduct(createProductRequest);
            apiResponse.setData("Product added successfully with ID: " + addedProduct.getId());
        } catch (RuntimeException e) {
            apiResponse.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("updateProduct/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable String productId, @RequestBody CreateProductRequest updateProductRequest) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            productService.updateProduct(productId, updateProductRequest);
            apiResponse.setData("Product updated successfully for ID: " + productId);
        } catch (RuntimeException e) {
            apiResponse.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

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

    @GetMapping("listAllProducts")
    public ResponseEntity<ApiResponse> listAllProducts() {
        ApiResponse apiResponse = new ApiResponse();
        List<ProductResponse> products = productService.getAllProducts();
        if (products.isEmpty()) {
            apiResponse.setErrorMessage("No products available.");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        apiResponse.setData(products);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
