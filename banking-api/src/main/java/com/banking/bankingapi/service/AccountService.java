package com.banking.bankingapi.service;

import com.banking.bankingapi.dto.AccountResponse;
import com.banking.bankingapi.dto.CreateAccountRequest;
import com.banking.bankingapi.exception.AccountNotFoundException;
import com.banking.bankingapi.exception.DuplicateAccountException;
import com.banking.bankingapi.exception.InvalidAccountStateException;
import com.banking.bankingapi.model.Account;
import com.banking.bankingapi.model.AccountStatus;
import com.banking.bankingapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    
    private final AccountRepository accountRepository;
    private static final SecureRandom RANDOM = new SecureRandom();
    
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        log.info("Creating new account for holder: {}", request.getHolderName());
        
        String accountNumber = generateAccountNumber();
        
        while (accountRepository.existsByAccountNumber(accountNumber)) {
            accountNumber = generateAccountNumber();
        }
        
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(request.getInitialDeposit())
                .accountType(request.getAccountType())
                .status(AccountStatus.ACTIVE)
                .holderName(request.getHolderName())
                .holderEmail(request.getHolderEmail())
                .build();
        
        Account savedAccount = accountRepository.save(account);
        log.info("Account created successfully with account number: {}", accountNumber);
        
        return mapToResponse(savedAccount);
    }
    
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        log.info("Fetching account with ID: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));
        return mapToResponse(account);
    }
    
    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String accountNumber) {
        log.info("Fetching account with number: {}", accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
        return mapToResponse(account);
    }
    
    @Transactional(readOnly = true)
    public Page<AccountResponse> getAllAccounts(Pageable pageable) {
        log.info("Fetching all accounts with pagination");
        return accountRepository.findAll(pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<AccountResponse> getAccountsByStatus(AccountStatus status, Pageable pageable) {
        log.info("Fetching accounts with status: {}", status);
        return accountRepository.findByStatus(status, pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional
    public void updateAccountStatus(String accountNumber, AccountStatus newStatus) {
        log.info("Updating account {} status to {}", accountNumber, newStatus);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        
        account.setStatus(newStatus);
        accountRepository.save(account);
    }
    
    @Transactional(readOnly = true)
    public Account getAccountEntityByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
    }
    
    @Transactional
    public Account getAccountWithLock(String accountNumber) {
        return accountRepository.findByAccountNumberWithLock(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
    }
    
    public void validateAccountIsActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountStateException(
                "Account " + account.getAccountNumber() + " is not active. Current status: " + account.getStatus()
            );
        }
    }
    
    private String generateAccountNumber() {
        long number = 1000000000L + (long)(RANDOM.nextDouble() * 9000000000L);
        return "ACC" + number;
    }
    
    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .holderName(account.getHolderName())
                .holderEmail(account.getHolderEmail())
                .createdDate(account.getCreatedDate())
                .updatedDate(account.getUpdatedDate())
                .build();
    }
}