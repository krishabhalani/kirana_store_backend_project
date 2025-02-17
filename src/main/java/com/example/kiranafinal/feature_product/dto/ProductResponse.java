package com.example.kiranafinal.feature_product.dto;

import lombok.*;

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

