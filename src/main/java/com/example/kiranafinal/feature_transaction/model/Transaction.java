package com.example.kiranafinal.feature_transaction.model;

import com.example.kiranafinal.feature_OrderProduct.enums.PaymentStatus;
import com.example.kiranafinal.feature_transaction.enums.PaymentMethod;
import com.example.kiranafinal.feature_transaction.enums.TransactionStatus;
import com.example.kiranafinal.feature_transaction.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

/**
 * Represents a transaction in the system.
 */
@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    /**
     * Unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionID;

    /**
     * ID of the user associated with this transaction.
     */
    @Column(nullable = false)
    private String userID;

    /**
     * ID of the order linked to this transaction.
     */
    @Column(nullable = false)
    private String orderID;

    /**
     * Date and time when the transaction was created.
     */
    @Column(nullable = false)
    private Instant transactionDate = Instant.now();

    /**
     * Type of the transaction (e.g., PURCHASE, REFUND).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    /**
     * Total price of the transaction in INR.
     */
    @Column(nullable = false)
    private Double totalPriceINR;

    /**
     * Total price of the transaction in USD.
     */
    private Double totalPriceUSD;

    /**
     * Currency conversion rate applied during the transaction.
     */
    private Double currencyConversion;

    /**
     * Payment method used for the transaction (e.g., CREDIT_CARD, UPI).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    /**
     * Status of the transaction (e.g., PENDING, COMPLETED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    /**
     * Payment status of the transaction (e.g., SUCCESS, FAILED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
}
