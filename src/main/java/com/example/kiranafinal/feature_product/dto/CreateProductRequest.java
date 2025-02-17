package com.example.kiranafinal.feature_product.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;
    private String category;
    private double price;
    private int quantity;
}
