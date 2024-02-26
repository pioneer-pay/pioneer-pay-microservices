package com.wu.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitiateTransfer {
    private String fromAccountId;
    private String toAccountId;
    private double amount;
    private String status;
}
