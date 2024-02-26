package com.wu.transaction.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wu.transaction.entity.Transaction;
import com.wu.transaction.repository.TransactionRepository;
import com.wu.transaction.service.TransactionService;

@Component
public class ScheduledTask {

    @Autowired
    private TransactionService transactionService;

    @Autowired TransactionRepository transactionRepository;

    @Scheduled(fixedRate = 45 * 1000)
    public void processPendingTransfers() throws Exception {
        List<Transaction> pendingTransactions = transactionRepository.findByStatus("PENDING");

        for (Transaction transaction : pendingTransactions) {
            transactionService.completeTransfer(transaction.getTransactionId());
        }
    }
}
