package com.banking.bankingapi.controller;

import com.banking.bankingapi.dto.AccountResponse;
import com.banking.bankingapi.dto.CreateAccountRequest;
import com.banking.bankingapi.model.AccountStatus;
import com.banking.bankingapi.service.AccountService;
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
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management", description = "APIs for managing bank accounts")
public class AccountController {
    
    private final AccountService accountService;
    
    @PostMapping
    @Operation(summary = "Create new account", description = "Creates a new bank account with initial deposit")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID", description = "Retrieves account details by account ID")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/number/{accountNumber}")
    @Operation(summary = "Get account by account number", description = "Retrieves account details by account number")
    public ResponseEntity<AccountResponse> getAccountByNumber(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all accounts", description = "Retrieves all accounts with pagination")
    public ResponseEntity<Page<AccountResponse>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("ASC") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AccountResponse> response = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get accounts by status", description = "Retrieves accounts filtered by status")
    public ResponseEntity<Page<AccountResponse>> getAccountsByStatus(
            @PathVariable AccountStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<AccountResponse> response = accountService.getAccountsByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{accountNumber}/status")
    @Operation(summary = "Update account status", description = "Updates the status of an account")
    public ResponseEntity<Void> updateAccountStatus(
            @PathVariable String accountNumber,
            @RequestParam AccountStatus status) {
        
        accountService.updateAccountStatus(accountNumber, status);
        return ResponseEntity.ok().build();
    }
}