package com.example.kiranafinal.feature_transaction.dto;

import com.example.kiranafinal.feature_transaction.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private String orderId; // ✅ Required for transaction creation
    private String userId;
    private TransactionType transactionType;
}
