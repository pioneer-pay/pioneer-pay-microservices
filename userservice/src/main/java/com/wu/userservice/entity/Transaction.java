package com.wu.userservice.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long transactionId;
    private String fromAccountId;
    private String toAccountId;
    private LocalDateTime dateTime;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double amount;
    private double transferedAmount;
    private double commission;
    private String status;
}
