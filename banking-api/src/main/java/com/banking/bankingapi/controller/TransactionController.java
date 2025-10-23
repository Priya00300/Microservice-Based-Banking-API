package com.banking.bankingapi.controller;

import com.banking.bankingapi.dto.TransactionRequest;
import com.banking.bankingapi.dto.TransactionResponse;
import com.banking.bankingapi.dto.TransferRequest;
import com.banking.bankingapi.model.TransactionType;
import com.banking.bankingapi.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "APIs for managing transactions")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @PostMapping("/deposit")
    @Operation(summary = "Deposit money", description = "Deposits money into an account")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.deposit(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw money", description = "Withdraws money from an account")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.withdraw(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/transfer")
    @Operation(summary = "Transfer money", description = "Transfers money between two accounts")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        TransactionResponse response = transactionService.transfer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/account/{accountNumber}")
    @Operation(summary = "Get transaction history", description = "Retrieves transaction history for an account")
    public ResponseEntity<Page<TransactionResponse>> getTransactionHistory(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("transactionDate").descending());
        Page<TransactionResponse> response = transactionService.getTransactionHistory(accountNumber, pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/account/{accountNumber}/type/{type}")
    @Operation(summary = "Get transactions by type", description = "Retrieves transactions filtered by type")
    public ResponseEntity<Page<TransactionResponse>> getTransactionsByType(
            @PathVariable String accountNumber,
            @PathVariable TransactionType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("transactionDate").descending());
        Page<TransactionResponse> response = transactionService.getTransactionsByType(accountNumber, type, pageable);
        return ResponseEntity.ok(response);
    }
}