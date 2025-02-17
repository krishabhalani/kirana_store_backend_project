package com.example.kiranafinal.feature_OrderProduct.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "order_products")
public class OrderProduct {

    @Id
    private String orderedProductID;
    private String transactionID;

    private String productID;
    private String userID;  // ✅ Store User ID
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Instant orderDate;
    private PaymentStatus paymentStatus;
}
