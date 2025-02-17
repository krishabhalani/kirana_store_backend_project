package com.example.kiranafinal.feature_transaction.repo;

import com.example.kiranafinal.feature_transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserID(String userID);  // Ensure this method exists in the repository
    // Fetch transactions within a date range
    List<Transaction> findByTransactionDateBetween(Instant startDate, Instant endDate);
}
