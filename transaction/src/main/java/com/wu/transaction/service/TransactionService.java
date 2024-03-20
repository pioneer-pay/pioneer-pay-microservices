package com.wu.transaction.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import com.wu.transaction.entity.SendMoneyReminder;
import com.wu.transaction.entity.Summary;
import com.wu.transaction.entity.Transaction;
import com.wu.transaction.payload.ApiResponse;

public interface TransactionService {
    
    ApiResponse initiateTransfer(Transaction transaction);

    ApiResponse saveReminder(SendMoneyReminder sendMoneyReminder);

    ApiResponse completeTransfer(Long transactionId) throws Exception;
    
    List<Transaction> getTransactionHistoryByAccountId(String accountId);
   
    Summary getSummary(String baseCurrencyCode, String targetCurrencyCode, Double amount);
}
