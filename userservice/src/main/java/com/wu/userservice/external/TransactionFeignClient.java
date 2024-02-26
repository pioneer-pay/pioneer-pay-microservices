package com.wu.userservice.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wu.userservice.entity.Transaction;

@FeignClient(name = "TRANSACTION-SERVICE")
public interface TransactionFeignClient {
    
    @GetMapping("/api/transaction/history/{accountId}")
    List<Transaction>showTransactionHistory(@PathVariable String accountId);
}
