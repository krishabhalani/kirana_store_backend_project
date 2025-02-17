package com.example.kiranafinal.feature_OrderProduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class OrderProductResponse {
    private String orderedProductID;
    private String productID;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Instant orderDate;

}
