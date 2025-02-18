package com.example.kiranafinal.feature_transaction.repo;

import com.example.kiranafinal.feature_transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

/**
 * Repository for managing transactions in the database.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    /**
     * Retrieves a list of transactions for a specific user.
     *
     * @param userID The ID of the user.
     * @return A list of transactions associated with the given user ID.
     */
    List<Transaction> findByUserID(String userID);

    /**
     * Retrieves transactions that occurred within a specific date range.
     *
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @return A list of transactions within the given date range.
     */
    List<Transaction> findByTransactionDateBetween(Instant startDate, Instant endDate);
}
