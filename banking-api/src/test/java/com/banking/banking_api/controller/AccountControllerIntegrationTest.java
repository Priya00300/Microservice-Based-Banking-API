package com.banking.bankingapi.controller;

import com.banking.bankingapi.dto.CreateAccountRequest;
import com.banking.bankingapi.model.AccountType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AccountControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void createAccount_Success() throws Exception {
        CreateAccountRequest request = CreateAccountRequest.builder()
                .holderName("Test User")
                .holderEmail("test@email.com")
                .accountType(AccountType.SAVINGS)
                .initialDeposit(new BigDecimal("1000.00"))
                .build();
        
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.holderName").value("Test User"))
                .andExpect(jsonPath("$.accountNumber").exists())
                .andExpect(jsonPath("$.balance").value(1000.00));
    }
    
    @Test
    void createAccount_ValidationError() throws Exception {
        CreateAccountRequest request = CreateAccountRequest.builder()
                .holderName("")
                .accountType(AccountType.SAVINGS)
                .initialDeposit(new BigDecimal("-100.00"))
                .build();
        
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void getAllAccounts_Success() throws Exception {
        mockMvc.perform(get("/api/accounts")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}