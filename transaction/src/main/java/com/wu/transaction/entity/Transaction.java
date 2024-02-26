package com.wu.transaction.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
