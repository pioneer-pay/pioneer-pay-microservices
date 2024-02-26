package com.wu.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String accountId;
    private String accountHolderName;
    private String userId;
    private String accountNo;
    private String ifscCode;
    private String bankName;
    private Double balance;
}
