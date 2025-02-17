package com.example.kiranafinal.feature_transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionReportResponse {
    private String period;  // Weekly / Monthly / Yearly
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal netFlow;
}
