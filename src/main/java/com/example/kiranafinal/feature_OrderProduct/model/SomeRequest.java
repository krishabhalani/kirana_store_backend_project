package com.example.kiranafinal.feature_OrderProduct.model;

import lombok.Data;
import com.example.kiranafinal.feature_OrderProduct.model.PaymentStatus;

@Data
public class SomeRequest {
    String productId;
    int quantity;
    String userId;
    PaymentStatus paymentStatus;  // ✅ Allow payment status to be passed in the request
}
