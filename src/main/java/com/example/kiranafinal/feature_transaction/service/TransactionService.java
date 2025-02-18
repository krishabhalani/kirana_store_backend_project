package com.example.kiranafinal.feature_transaction.service;

import com.example.kiranafinal.feature_transaction.dto.TransactionReportResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for handling transactions.
 */
public interface TransactionService {

    /**
     * Creates a new transaction based on the provided request.
     *
     * @param request The transaction details.
     * @return The created transaction details.
     */
    TransactionResponse addTransaction(TransactionRequest request);

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionID The ID of the transaction.
     * @return The transaction details.
     */
    TransactionResponse getTransactionById(String transactionID);

    /**
     * Retrieves a list of all transactions.
     *
     * @return A list of all transactions.
     */
    List<TransactionResponse> listAllTransactions();

    /**
     * Retrieves all transactions for a specific user.
     *
     * @param userID The ID of the user.
     * @return A list of transactions for the specified user.
     */
    List<TransactionResponse> listUserTransactions(UUID userID);

    /**
     * Calculates the total amount spent by a user.
     *
     * @param userID The ID of the user.
     * @return The total amount spent by the user.
     */
    Double getTotalSpentByUser(UUID userID);

    /**
     * Converts an amount from one currency to another.
     *
     * @param amount      The amount to convert.
     * @param fromCurrency The source currency.
     * @param toCurrency   The target currency.
     * @return The converted amount.
     */
    Double convertCurrency(Double amount, String fromCurrency, String toCurrency);

    /**
     * Generates a transaction report for a given period.
     *
     * @param period The time period for the report.
     * @return The generated transaction report.
     */
    TransactionReportResponse generateReport(String period);

    /**
     * Deletes a transaction by its ID.
     *
     * @param transactionID The ID of the transaction to delete.
     */
    void deleteTransaction(UUID transactionID);
}
