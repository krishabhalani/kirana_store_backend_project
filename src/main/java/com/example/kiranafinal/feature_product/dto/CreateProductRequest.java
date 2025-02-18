package com.example.kiranafinal.feature_product.dto;

import lombok.*;
/**
 * DTO for representing  create product request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;
    private String category;
    private double price;
    private int quantity;
}
