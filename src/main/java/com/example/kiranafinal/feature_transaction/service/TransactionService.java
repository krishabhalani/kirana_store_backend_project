package com.example.kiranafinal.feature_transaction.service;

import com.example.kiranafinal.feature_transaction.dto.TransactionReportResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    // ✅ Create a transaction from an order
    TransactionResponse addTransaction(TransactionRequest request);

    // ✅ Get transaction details by ID
    TransactionResponse getTransactionById(String transactionID);

    // ✅ List all transactions
    List<TransactionResponse> listAllTransactions();

    // ✅ List all transactions for a specific user
    List<TransactionResponse> listUserTransactions(UUID userID);

    // ✅ Get total amount spent by a user
    Double getTotalSpentByUser(UUID userID);

    // ✅ Convert currency
    Double convertCurrency(Double amount, String fromCurrency, String toCurrency);

    //Repost Generation
    TransactionReportResponse generateReport(String period);

    // ✅ Delete a transaction
    void deleteTransaction(UUID transactionID);
}