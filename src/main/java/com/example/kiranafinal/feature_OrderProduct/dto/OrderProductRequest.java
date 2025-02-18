package com.example.kiranafinal.feature_OrderProduct.dto;

import lombok.Data;
import com.example.kiranafinal.feature_OrderProduct.enums.PaymentStatus;


/**
 * Represents a request for ordering a product.
 */
@Data
public class OrderProductRequest {
    String productId;
    int quantity;
    String userId;
    PaymentStatus paymentStatus;  // Allow payment status to be passed in the request
}
