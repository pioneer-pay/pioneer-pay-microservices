package com.wu.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountId;
    private String userId;
    private String accountNo;
    private String bankName;
    private Double balance;
    private String ifscCode;

}
