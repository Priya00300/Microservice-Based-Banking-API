package com.banking.bankingapi.dto;

import com.banking.bankingapi.model.AccountStatus;
import com.banking.bankingapi.model.AccountType;
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
public class AccountResponse {
    
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private AccountStatus status;
    private String holderName;
    private String holderEmail;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}