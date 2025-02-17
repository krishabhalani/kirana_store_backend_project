package com.example.kiranafinal.feature_transaction.dto;

import com.example.kiranafinal.feature_transaction.model.TransactionType;
import com.example.kiranafinal.feature_transaction.model.PaymentMethod;
import com.example.kiranafinal.feature_OrderProduct.model.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class TransactionResponse {
    private String transactionId;
    private String userId;
    private String orderId; // ✅ Include Order ID
    private Instant transactionDate;
    private TransactionType transactionType;
    private BigDecimal totalPriceInINR;
    private BigDecimal totalPriceInUSD;
    private PaymentMethod paymentMethod;
    private String status;  // ✅ Fix: Add status field
    private PaymentStatus paymentStatus;  // ✅ Fix: Add paymentStatus field

    // ✅ Constructor to fix the mapping issue
    public TransactionResponse(String transactionId, String userId, String orderId, Instant transactionDate,
                               TransactionType transactionType, BigDecimal totalPriceInINR,
                               BigDecimal totalPriceInUSD, PaymentMethod paymentMethod, String status,
                               PaymentStatus paymentStatus) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.orderId = orderId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.totalPriceInINR = totalPriceInINR;
        this.totalPriceInUSD = totalPriceInUSD;
        this.paymentMethod = paymentMethod;
        this.status = status;  // ✅ Fix: Ensure this field exists
        this.paymentStatus = paymentStatus;
    }
}
