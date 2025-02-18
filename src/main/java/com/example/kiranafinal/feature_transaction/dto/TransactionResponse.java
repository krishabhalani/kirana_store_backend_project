package com.example.kiranafinal.feature_transaction.dto;

import com.example.kiranafinal.feature_transaction.enums.TransactionType;
import com.example.kiranafinal.feature_transaction.enums.PaymentMethod;
import com.example.kiranafinal.feature_OrderProduct.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for representing a transaction response.
 */
@Getter
@Setter
public class TransactionResponse {

    /**
     * Unique identifier for the transaction.
     */
    private String transactionId;

    /**
     * Unique identifier for the user associated with the transaction.
     */
    private String userId;

    /**
     * Unique identifier for the order related to the transaction.
     */
    private String orderId;

    /**
     * Date and time when the transaction occurred.
     */
    private Instant transactionDate;

    /**
     * Type of the transaction (e.g., PURCHASE, REFUND).
     */
    private TransactionType transactionType;

    /**
     * Total transaction amount in INR.
     */
    private BigDecimal totalPriceInINR;

    /**
     * Total transaction amount in USD.
     */
    private BigDecimal totalPriceInUSD;

    /**
     * Payment method used for the transaction (e.g., CREDIT_CARD, UPI).
     */
    private PaymentMethod paymentMethod;

    /**
     * Current status of the transaction.
     */
    private String status;

    /**
     * Payment status of the transaction (e.g., PENDING, COMPLETED).
     */
    private PaymentStatus paymentStatus;

    /**
     * Constructor to initialize all fields.
     */
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
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
}
