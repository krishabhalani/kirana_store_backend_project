package com.example.kiranafinal.feature_transaction.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionReportResponse;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.dto.TransactionResponse;
import com.example.kiranafinal.feature_transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("createTransaction")
    public ResponseEntity<ApiResponse> createTransaction(@RequestBody TransactionRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.addTransaction(request));
        return ResponseEntity.ok(apiResponse);
    }


    // Get a specific transaction by ID
    @GetMapping("getTransaction/{transactionID}")
    public ResponseEntity<ApiResponse> getTransaction(@PathVariable String transactionID) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.getTransactionById(transactionID));
        return ResponseEntity.ok(apiResponse);
    }

    // Get all transactions
    @GetMapping("listAllTransactions")
    public ResponseEntity<ApiResponse> listAllTransactions() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.listAllTransactions());
        return ResponseEntity.ok(apiResponse);
    }

    // Get all transactions for a specific user
    @GetMapping("listUserTransactions/{userID}")
    public ResponseEntity<ApiResponse> listUserTransactions(@PathVariable UUID userID) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.listUserTransactions(userID));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{period}")
    public ResponseEntity<ApiResponse> getTransactionReport(@PathVariable String period) {
        ApiResponse apiResponse = new ApiResponse();
        TransactionReportResponse report = transactionService.generateReport(period);
        apiResponse.setData(report);
        return ResponseEntity.ok(apiResponse);
    }

    // Get total amount spent by a user
    @GetMapping("getTotalSpentByUser/{userID}")
    public ResponseEntity<ApiResponse> getTotalSpentByUser(@PathVariable UUID userID) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.getTotalSpentByUser(userID));
        return ResponseEntity.ok(apiResponse);
    }


    // ✅ Fixed Convert Currency API
    @GetMapping("/convertCurrency")
    public ResponseEntity<ApiResponse> convertCurrency(
            @RequestParam(name = "amount", required = true) Double amount,
            @RequestParam(name = "fromCurrency", required = true) String fromCurrency,
            @RequestParam(name = "toCurrency", required = true) String toCurrency) {

        // 🚀 Ensure required parameters are present
        if (amount == null || fromCurrency == null || toCurrency == null) {
            throw new IllegalArgumentException("❌ Required parameters (amount, fromCurrency, toCurrency) are missing.");
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(transactionService.convertCurrency(amount, fromCurrency, toCurrency));
        return ResponseEntity.ok(apiResponse);
    }
}
