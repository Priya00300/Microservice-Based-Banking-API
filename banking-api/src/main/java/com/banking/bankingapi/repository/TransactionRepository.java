package com.banking.bankingapi.repository;

import com.banking.bankingapi.model.Transaction;
import com.banking.bankingapi.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
    
    Page<Transaction> findByAccountIdAndType(Long accountId, TransactionType type, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId " +
           "AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountIdAndDateRange(
        Long accountId, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
    
    @Query("SELECT t FROM Transaction t WHERE t.referenceNumber = :referenceNumber")
    Transaction findByReferenceNumber(String referenceNumber);
}