package com.banking.bankingapi.dto;

import com.banking.bankingapi.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime transactionDate;
    private String description;
    private String referenceNumber;
    private BigDecimal balanceAfter;
    private String accountNumber;
    private String relatedAccountNumber;
}