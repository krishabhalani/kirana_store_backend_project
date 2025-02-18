package com.example.kiranafinal.feature_transaction.dao;

import com.example.kiranafinal.feature_transaction.model.Transaction;
import com.example.kiranafinal.feature_transaction.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing transactions.
 */
@Repository
public class TransactionDAO {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Saves a transaction to the database.
     *
     * @param transaction The transaction to save.
     * @return The saved transaction.
     */
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionID The ID of the transaction.
     * @return An Optional containing the transaction if found.
     */
    public Optional<Transaction> findById(UUID transactionID) {
        return transactionRepository.findById(String.valueOf(transactionID));
    }

    /**
     * Retrieves all transactions.
     *
     * @return A list of all transactions.
     */
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    /**
     * Retrieves all transactions for a given user.
     *
     * @param userID The ID of the user.
     * @return A list of transactions for the user.
     */
    public List<Transaction> findByUserId(String userID) {
        return transactionRepository.findByUserID(userID);
    }

    /**
     * Calculates the total amount spent by a user.
     *
     * @param userID The ID of the user.
     * @return The total amount spent by the user.
     */
    public Double getTotalSpentByUser(UUID userID) {
        return transactionRepository.findByUserID(userID.toString()).stream()
                .mapToDouble(Transaction::getTotalPriceINR)
                .sum();
    }

    /**
     * Deletes a transaction by its ID.
     *
     * @param transactionID The ID of the transaction to delete.
     */
    public void deleteById(UUID transactionID) {
        transactionRepository.deleteById(String.valueOf(transactionID));
    }

    /**
     * Retrieves transactions within a specified date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return A list of transactions within the date range.
     */
    public List<Transaction> findByTransactionDateBetween(Instant startDate, Instant endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }
}
