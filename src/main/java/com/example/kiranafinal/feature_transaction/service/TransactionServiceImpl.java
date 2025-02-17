package com.example.kiranafinal.feature_transaction.service;

import com.example.kiranafinal.feature_OrderProduct.model.PaymentStatus;
import com.example.kiranafinal.feature_OrderProduct.repo.OrderProductRepository;
import com.example.kiranafinal.feature_transaction.dao.TransactionDAO;
import com.example.kiranafinal.feature_transaction.dto.TransactionReportResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.dto.TransactionResponse;
import com.example.kiranafinal.feature_transaction.model.PaymentMethod;
import com.example.kiranafinal.feature_transaction.model.Transaction;
import com.example.kiranafinal.feature_transaction.model.TransactionStatus;
import com.example.kiranafinal.feature_transaction.model.TransactionType;
import com.example.kiranafinal.feature_transaction.util.CurrencyConverter;
import com.example.kiranafinal.feature_OrderProduct.dao.OrderProductDAO;
import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDAO transactionDAO; // Use DAO instead of repository

    @Autowired
    private OrderProductDAO orderProductDAO; // ✅ Fetch Order details from DAO

    @Autowired
    private CurrencyConverter currencyConverter; // Cached currency conversion service
    @Autowired
    private OrderProductRepository orderProductRepository;

    /**
     * 🎯 **Creates a transaction when an order is placed.**
     */
    @Override
//    @CachePut(value = "transactions", key = "#result.transactionId")
    public TransactionResponse addTransaction(TransactionRequest request) {
        System.out.println("🚀 [addTransaction] Received Request: " + request);

        // Fetch the order from MongoDB
        ObjectId requestOderId = new ObjectId(request.getOrderId());
        OrderProduct order = orderProductRepository.findByorderedProductID(requestOderId);
//                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + request.getOrderId()));


        System.out.println("🛒 [addTransaction] Found Order: " + order);

        // ✅ Ensure payment is SUCCESS before transaction
        if (order.getPaymentStatus() != PaymentStatus.SUCCESS) {
            System.out.println("❌ [addTransaction] Order Payment Status: " + order.getPaymentStatus());
            throw new RuntimeException("❌ Payment not completed for this order.");
        }

        System.out.println("✅ [addTransaction] Payment is SUCCESS. Proceeding with transaction...");

        Transaction transaction = new Transaction();
        transaction.setUserID(request.getUserId());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setTransactionDate(Instant.now());
        transaction.setTotalPriceINR(order.getTotalPrice().doubleValue());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setPaymentStatus(PaymentStatus.SUCCESS);
        transaction.setOrderID(request.getOrderId());
        transaction.setPaymentMethod(PaymentMethod.CASH);

        // ✅ Fetch the exchange rate (Cached API)
        Double conversionRate = currencyConverter.getExchangeRate("INR", "USD");

        if (conversionRate != null) {
            transaction.setCurrencyConversion(conversionRate);
            transaction.setTotalPriceUSD(order.getTotalPrice().multiply(BigDecimal.valueOf(conversionRate)).doubleValue());
        } else {
            transaction.setTotalPriceUSD(0.0); // Default to 0 if conversion fails
        }

        System.out.println(" [addTransaction] Creating Transaction: " + transaction);

        // ✅ Save the transaction
        Transaction savedTransaction = transactionDAO.save(transaction);

        System.out.println(" [addTransaction] Transaction Created Successfully! ID: " + savedTransaction.getTransactionID());

        return mapToResponse(savedTransaction);
    }

    @Override
    public TransactionReportResponse generateReport(String period) {
        Instant startDate;
        Instant endDate = Instant.now();  // Current timestamp

        switch (period.toLowerCase()) {
            case "weekly":
                startDate = LocalDate.now().minusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
                break;
            case "monthly":
                startDate = LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
                break;
            case "yearly":
                startDate = LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
                break;
            default:
                throw new IllegalArgumentException("Invalid period: Choose 'weekly', 'monthly', or 'yearly'");
        }

        List<Transaction> transactions = transactionDAO.findByTransactionDateBetween(startDate, endDate);

        BigDecimal totalCredits = BigDecimal.ZERO;
        BigDecimal totalDebits = BigDecimal.ZERO;

        for (Transaction txn : transactions) {
            if (txn.getTransactionType() == TransactionType.CREDIT) {
                totalCredits = totalCredits.add(BigDecimal.valueOf(txn.getTotalPriceINR()));
            } else if (txn.getTransactionType() == TransactionType.DEBIT) {
                totalDebits = totalDebits.add(BigDecimal.valueOf(txn.getTotalPriceINR()));
            }
        }

        BigDecimal netFlow = totalDebits.subtract(totalCredits);

        return new TransactionReportResponse(period, totalCredits, totalDebits, netFlow);
    }



    @Override
//    @Cacheable(value = "transactions", key = "#transactionID")
    public TransactionResponse getTransactionById(String transactionID) {
        return transactionDAO.findById(UUID.fromString(transactionID))
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public List<TransactionResponse> listAllTransactions() {
        return transactionDAO.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> listUserTransactions(UUID userID) {
        return transactionDAO.findByUserId(userID.toString())  // ✅ Convert UUID to String
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
//    @Cacheable(value = "user-spending", key = "#userID")
    public Double getTotalSpentByUser(UUID userID) {
        return transactionDAO.getTotalSpentByUser(userID);
    }

    @Override
    public Double convertCurrency(Double amount, String fromCurrency, String toCurrency) {
        if (amount == null || fromCurrency == null || toCurrency == null) {
            throw new IllegalArgumentException("❌ Invalid input: Amount, fromCurrency, and toCurrency are required.");
        }

        System.out.println("🔹 Converting " + amount + " " + fromCurrency + " -> " + toCurrency);

        Double exchangeRate = currencyConverter.getExchangeRate(fromCurrency, toCurrency);

        if (exchangeRate == null) {
            throw new RuntimeException("❌ Exchange rate not available for " + fromCurrency + " -> " + toCurrency);
        }

        return amount * exchangeRate;
    }





    @Override
//    @CacheEvict(value = "transactions", key = "#transactionID")
    public void deleteTransaction(UUID transactionID) {
        transactionDAO.deleteById(transactionID);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionID(),
                transaction.getUserID(),
                transaction.getOrderID(),  // ✅ Include Order ID
                transaction.getTransactionDate(),
                transaction.getTransactionType(),
                BigDecimal.valueOf(transaction.getTotalPriceINR()),
                BigDecimal.valueOf(transaction.getTotalPriceUSD()),
                transaction.getPaymentMethod(),
                transaction.getStatus().name(),
                transaction.getPaymentStatus()  // ✅ Include Payment Status
        );
    }

}
