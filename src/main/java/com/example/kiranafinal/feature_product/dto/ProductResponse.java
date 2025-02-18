package com.example.kiranafinal.feature_product.dto;

import lombok.*;

/**
 * DTO for representing product response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String category;
    private double price;
    private int quantity;
}

