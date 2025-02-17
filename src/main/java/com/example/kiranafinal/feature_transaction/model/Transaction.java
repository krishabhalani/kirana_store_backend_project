package com.example.kiranafinal.feature_transaction.model;

import com.example.kiranafinal.feature_OrderProduct.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)  // ✅ Use UUID format for ID
    private String transactionID;  // ✅ Store as String

    @Column(nullable = false)
    private String userID; // ✅ Store as String since MongoDB uses String IDs

    @Column(nullable = false)
    private String orderID; // ✅ Link to Order ID

    @Column(nullable = false)
    private Instant transactionDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Double totalPriceINR;

    private Double totalPriceUSD;
    private Double currencyConversion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;




}
