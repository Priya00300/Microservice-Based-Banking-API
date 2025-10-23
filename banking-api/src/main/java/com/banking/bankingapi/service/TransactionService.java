package com.banking.bankingapi.service;

import com.banking.bankingapi.dto.TransactionRequest;
import com.banking.bankingapi.dto.TransactionResponse;
import com.banking.bankingapi.dto.TransferRequest;
import com.banking.bankingapi.model.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime; // <-- CORRECTED IMPORT
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    // We inject AccountService because the real implementation will need it
    private final AccountService accountService;

    /**
     * DUMMY IMPLEMENTATION for deposit
     */
    public TransactionResponse deposit(TransactionRequest request) {
        log.warn("DUMMY IMPLEMENTATION: deposit called for {}", request.getAccountNumber());
        // We just return a fake response to satisfy the controller
        return createDummyResponse(request.getAccountNumber(), request.getAmount(), TransactionType.DEPOSIT);
    }

    /**
     * DUMMY IMPLEMENTATION for withdraw
     */
    public TransactionResponse withdraw(TransactionRequest request) {
        log.warn("DUMMY IMPLEMENTATION: withdraw called for {}", request.getAccountNumber());
        return createDummyResponse(request.getAccountNumber(), request.getAmount(), TransactionType.WITHDRAWAL);
    }

    /**
     * DUMMY IMPLEMENTATION for transfer
     */
    public TransactionResponse transfer(TransferRequest request) {
        log.warn("DUMMY IMPLEMENTATION: transfer called from {} to {}", request.getFromAccountNumber(), request.getToAccountNumber());
        return createDummyResponse(request.getFromAccountNumber(), request.getAmount(), TransactionType.TRANSFER_OUT);
    }

    /**
     * DUMMY IMPLEMENTATION for getTransactionHistory
     */
    public Page<TransactionResponse> getTransactionHistory(String accountNumber, Pageable pageable) {
        log.warn("DUMMY IMPLEMENTATION: getTransactionHistory called for {}", accountNumber);
        // Return an empty page
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    /**
     * DUMMY IMPLEMENTATION for getTransactionsByType
     */
    public Page<TransactionResponse> getTransactionsByType(String accountNumber, TransactionType type, Pageable pageable) {
        log.warn("DUMMY IMPLEMENTATION: getTransactionsByType called for {} with type {}", accountNumber, type);
        // Return an empty page
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }


    // Helper method to create a fake response
    private TransactionResponse createDummyResponse(String accNum, BigDecimal amount, TransactionType type) {
        return TransactionResponse.builder()
                .id(1L) // Dummy ID
                .accountNumber(accNum)
                .amount(amount)
                .type(type)
                .balanceAfter(new BigDecimal("1000.00")) // Dummy balance
                .description("Dummy Transaction")
                .referenceNumber("DUMMY-TXN-123")
                .transactionDate(LocalDateTime.now()) // <-- THIS IS THE FIX
                .build();
    }
}