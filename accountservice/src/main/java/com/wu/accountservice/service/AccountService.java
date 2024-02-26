package com.wu.accountservice.service;

import java.util.List;

import com.wu.accountservice.entity.Account;
import com.wu.accountservice.entity.UpdateRequest;
import com.wu.accountservice.payload.ApiResponse;

public interface AccountService {
    ApiResponse createAccount(Account account);

    List<Account> get(String userId);

    List<Account> getAll();

    Account getByAccountId(String accountId);

    List<Account>getAllExcept(String accountId);

    Double getBalance(String accountId);
    
    ApiResponse updateBalance(UpdateRequest updateRequest);

    ApiResponse updateAccountDetails(String userId,Account account);
   
}
