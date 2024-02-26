package com.wu.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wu.transaction.entity.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction,Long>{
    List<Transaction> findByFromAccountIdOrToAccountId(String fromAccountId, String toAccountId);

    Transaction findByTransactionId(Long transactionId);

    List<Transaction> findByStatus(String status);
}
