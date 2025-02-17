package com.example.kiranafinal.feature_transaction.dao;

import com.example.kiranafinal.feature_transaction.model.Transaction;
import com.example.kiranafinal.feature_transaction.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TransactionDAO {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

//    private static final String TRANSACTION_CACHE = "transactions";

    @Autowired
    private TransactionRepository transactionRepository;

    // Save transaction & update cache
//    @CachePut(value = TRANSACTION_CACHE, key = "#transaction.transactionID")
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Get transaction by ID with caching
//    @Cacheable(value = TRANSACTION_CACHE, key = "#transactionID")
    public Optional<Transaction> findById(UUID transactionID) {
        return transactionRepository.findById(String.valueOf(transactionID));
    }

    // Get all transactions (No caching for list retrieval)
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }


    public List<Transaction> findByUserId(String userID) {  // ✅ Use String instead of UUID
        return transactionRepository.findByUserID(userID);
    }

    // Get total spent by user (Can be cached if frequently used)
//    @Cacheable(value = "user-spending", key = "#userID")
    public Double getTotalSpentByUser(UUID userID) {
        return transactionRepository.findByUserID(userID.toString()).stream()
                .mapToDouble(Transaction::getTotalPriceINR)
                .sum();
    }

    // Invalidate cache when deleting a transaction
//    @CacheEvict(value = TRANSACTION_CACHE, key = "#transactionID")
    public void deleteById(UUID transactionID) {
        transactionRepository.deleteById(String.valueOf(transactionID));
    }

    public List<Transaction> findByTransactionDateBetween(Instant startDate, Instant endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }
}
