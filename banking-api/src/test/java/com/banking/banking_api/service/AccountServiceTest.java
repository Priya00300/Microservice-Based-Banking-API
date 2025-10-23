package com.banking.bankingapi.service;

import com.banking.bankingapi.dto.AccountResponse;
import com.banking.bankingapi.dto.CreateAccountRequest;
import com.banking.bankingapi.exception.AccountNotFoundException;
import com.banking.bankingapi.model.Account;
import com.banking.bankingapi.model.AccountStatus;
import com.banking.bankingapi.model.AccountType;
import com.banking.bankingapi.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    
    @Mock
    private AccountRepository accountRepository;
    
    @InjectMocks
    private AccountService accountService;
    
    private Account testAccount;
    private CreateAccountRequest createRequest;
    
    @BeforeEach
    void setUp() {
        testAccount = Account.builder()
                .id(1L)
                .accountNumber("ACC1234567890")
                .balance(new BigDecimal("1000.00"))
                .accountType(AccountType.SAVINGS)
                .status(AccountStatus.ACTIVE)
                .holderName("John Doe")
                .holderEmail("john@email.com")
                .build();
        
        createRequest = CreateAccountRequest.builder()
                .holderName("John Doe")
                .holderEmail("john@email.com")
                .accountType(AccountType.SAVINGS)
                .initialDeposit(new BigDecimal("1000.00"))
                .build();
    }
    
    @Test
    void createAccount_Success() {
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);
        
        AccountResponse response = accountService.createAccount(createRequest);
        
        assertNotNull(response);
        assertEquals("John Doe", response.getHolderName());
        assertEquals(AccountStatus.ACTIVE, response.getStatus());
        verify(accountRepository, times(1)).save(any(Account.class));
    }
    
    @Test
    void getAccountById_Success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        
        AccountResponse response = accountService.getAccountById(1L);
        
        assertNotNull(response);
        assertEquals("ACC1234567890", response.getAccountNumber());
        verify(accountRepository, times(1)).findById(1L);
    }
    
    @Test
    void getAccountById_NotFound() {
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccountById(999L);
        });
    }
    
    @Test
    void getAccountByNumber_Success() {
        when(accountRepository.findByAccountNumber("ACC1234567890"))
                .thenReturn(Optional.of(testAccount));
        
        AccountResponse response = accountService.getAccountByNumber("ACC1234567890");
        
        assertNotNull(response);
        assertEquals("John Doe", response.getHolderName());
    }
}