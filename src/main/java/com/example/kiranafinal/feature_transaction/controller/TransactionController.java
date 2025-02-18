package com.example.kiranafinal.feature_transaction.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionReportResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.kafka.KafkaProducerService;
import com.example.kiranafinal.feature_transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for managing transactions.
 */
@RestController
@RequestMapping("/v1/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final KafkaProducerService kafkaProducerService;

    /**
     * Constructor to initialize dependencies.
     */

    public TransactionController(TransactionService transactionService, KafkaProducerService kafkaProducerService) {
        this.transactionService = transactionService;
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Creates a new transaction.
     *
     * @param request The transaction details.
     * @return ResponseEntity containing the created transaction.
     */
    @PostMapping("addTransaction")
    public ResponseEntity<ApiResponse> createTransaction(@RequestBody TransactionRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.addTransaction(request));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionID The ID of the transaction.
     * @return ResponseEntity containing the transaction details.
     */
    @GetMapping("getTransaction/{transactionID}")
    public ResponseEntity<ApiResponse> getTransaction(@PathVariable String transactionID) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.getTransactionById(transactionID));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves all transactions.
     *
     * @return ResponseEntity containing a list of all transactions.
     */
    @GetMapping("listAllTransactions")
    public ResponseEntity<ApiResponse> listAllTransactions() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.listAllTransactions());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves all transactions for a specific user.
     *
     * @param userID The ID of the user.
     * @return ResponseEntity containing a list of the user's transactions.
     */
    @GetMapping("listUserTransactions/{userID}")
    public ResponseEntity<ApiResponse> listUserTransactions(@PathVariable UUID userID) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.listUserTransactions(userID));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     *
     */
    @GetMapping("/{period}")
    public ResponseEntity<ApiResponse> getTransactionReport(@PathVariable String period) {
        ApiResponse apiResponse = new ApiResponse();
        TransactionReportResponse report = transactionService.generateReport(period);
        apiResponse.setData(report);

        // Send report to Kafka topic
        kafkaProducerService.sendReportMessage(period, report.toString());

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the total amount spent by a user.
     *
     * @param userID The ID of the user.
     * @return ResponseEntity containing the total amount spent.
     */
    @GetMapping("getTotalSpentByUser/{userID}")
    public ResponseEntity<ApiResponse> getTotalSpentByUser(@PathVariable UUID userID) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.getTotalSpentByUser(userID));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Converts currency from one type to another.
     *
     * @param amount       The amount to convert.
     * @param fromCurrency The currency to convert from.
     * @param toCurrency   The currency to convert to.
     * @return ResponseEntity containing the converted amount.
     * @throws IllegalArgumentException if required parameters are missing.
     */
    @GetMapping("/convertCurrency")
    public ResponseEntity<ApiResponse> convertCurrency(
            @RequestParam(name = "amount") Double amount,
            @RequestParam(name = "fromCurrency") String fromCurrency,
            @RequestParam(name = "toCurrency") String toCurrency) {

        if (amount == null || fromCurrency == null || toCurrency == null) {
            throw new IllegalArgumentException("Required parameters (amount, fromCurrency, toCurrency) are missing.");
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.convertCurrency(amount, fromCurrency, toCurrency));
        return ResponseEntity.ok(apiResponse);
    }
}
