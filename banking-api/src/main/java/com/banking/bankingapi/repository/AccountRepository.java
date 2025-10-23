package com.banking.bankingapi.repository;

import com.banking.bankingapi.model.Account;
import com.banking.bankingapi.model.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberWithLock(String accountNumber);
    
    boolean existsByAccountNumber(String accountNumber);
    
    Page<Account> findByStatus(AccountStatus status, Pageable pageable);
    
    @Query("SELECT a FROM Account a WHERE a.holderName LIKE %:name%")
    Page<Account> searchByHolderName(String name, Pageable pageable);
}