package com.example.kiranafinal.feature_transaction.service;

import com.example.kiranafinal.feature_OrderProduct.enums.PaymentStatus;
import com.example.kiranafinal.feature_OrderProduct.repo.OrderProductRepository;
import com.example.kiranafinal.feature_transaction.dao.TransactionDAO;
import com.example.kiranafinal.feature_transaction.dto.TransactionReportResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.dto.TransactionResponse;
import com.example.kiranafinal.feature_transaction.enums.PaymentMethod;
import com.example.kiranafinal.feature_transaction.model.Transaction;
import com.example.kiranafinal.feature_transaction.enums.TransactionStatus;
import com.example.kiranafinal.feature_transaction.enums.TransactionType;
import com.example.kiranafinal.feature_transaction.util.CurrencyConverter;
import com.example.kiranafinal.feature_OrderProduct.dao.OrderProductDAO;
import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for handling transaction operations.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private OrderProductDAO orderProductDAO;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Autowired
    private OrderProductRepository orderProductRepository;

    /**
     * Creates a transaction for an order.
     *
     * @param request The transaction request details.
     * @return The created transaction response.
     * @throws RuntimeException if the order payment is not successful.
     */
    @Override
    public TransactionResponse addTransaction(TransactionRequest request) {
        ObjectId requestOrderId = new ObjectId(request.getOrderId());
        OrderProduct order = orderProductRepository.findByorderedProductID(requestOrderId);

        if (order.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Payment not completed for this order.");
        }

        Transaction transaction = new Transaction();
        transaction.setUserID(request.getUserId());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setTransactionDate(Instant.now());
        transaction.setTotalPriceINR(order.getTotalPrice().doubleValue());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setPaymentStatus(PaymentStatus.SUCCESS);
        transaction.setOrderID(request.getOrderId());
        transaction.setPaymentMethod(PaymentMethod.CASH);

        Double conversionRate = currencyConverter.getExchangeRate("INR", "USD");

        if (conversionRate != null) {
            transaction.setCurrencyConversion(conversionRate);
            transaction.setTotalPriceUSD(order.getTotalPrice().multiply(BigDecimal.valueOf(conversionRate)).doubleValue());
        } else {
            transaction.setTotalPriceUSD(0.0);
        }

        Transaction savedTransaction = transactionDAO.save(transaction);
        return mapToResponse(savedTransaction);
    }

    /**
     * Generates a transaction report based on the given period.
     *
     * @param period The time period for the report (weekly, monthly, or yearly).
     * @return The transaction report response.
     * @throws IllegalArgumentException if an invalid period is provided.
     */
    @Override
    public TransactionReportResponse generateReport(String period) {
        Instant startDate;
        Instant endDate = Instant.now();

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

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionID The ID of the transaction.
     * @return The transaction response.
     * @throws RuntimeException if the transaction is not found.
     */
    @Override
    public TransactionResponse getTransactionById(String transactionID) {
        return transactionDAO.findById(UUID.fromString(transactionID))
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    /**
     * Lists all transactions.
     *
     * @return A list of all transaction responses.
     */
    @Override
    public List<TransactionResponse> listAllTransactions() {
        return transactionDAO.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lists all transactions for a specific user.
     *
     * @param userID The user ID.
     * @return A list of transaction responses for the user.
     */
    @Override
    public List<TransactionResponse> listUserTransactions(UUID userID) {
        return transactionDAO.findByUserId(userID.toString())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total amount spent by a user.
     *
     * @param userID The user ID.
     * @return The total amount spent by the user.
     */
    @Override
    public Double getTotalSpentByUser(UUID userID) {
        return transactionDAO.getTotalSpentByUser(userID);
    }

    /**
     * Converts currency from one type to another.
     *
     * @param amount       The amount to convert.
     * @param fromCurrency The source currency.
     * @param toCurrency   The target currency.
     * @return The converted amount.
     * @throws IllegalArgumentException if any parameter is null.
     * @throws RuntimeException if the exchange rate is not available.
     */
    @Override
    public Double convertCurrency(Double amount, String fromCurrency, String toCurrency) {
        if (amount == null || fromCurrency == null || toCurrency == null) {
            throw new IllegalArgumentException("Invalid input: Amount, fromCurrency, and toCurrency are required.");
        }

        Double exchangeRate = currencyConverter.getExchangeRate(fromCurrency, toCurrency);

        if (exchangeRate == null) {
            throw new RuntimeException("Exchange rate not available for " + fromCurrency + " -> " + toCurrency);
        }

        return amount * exchangeRate;
    }

    /**
     * Deletes a transaction by its ID.
     *
     * @param transactionID The transaction ID to delete.
     */
    @Override
    public void deleteTransaction(UUID transactionID) {
        transactionDAO.deleteById(transactionID);
    }

    /**
     * Maps a Transaction entity to a TransactionResponse DTO.
     *
     * @param transaction The transaction entity.
     * @return The transaction response object.
     */
    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionID(),
                transaction.getUserID(),
                transaction.getOrderID(),
                transaction.getTransactionDate(),
                transaction.getTransactionType(),
                BigDecimal.valueOf(transaction.getTotalPriceINR()),
                BigDecimal.valueOf(transaction.getTotalPriceUSD()),
                transaction.getPaymentMethod(),
                transaction.getStatus().name(),
                transaction.getPaymentStatus()
        );
    }
}
