package com.wu.accountservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wu.accountservice.entity.Account;
import com.wu.accountservice.entity.UpdateRequest;
import com.wu.accountservice.exception.AlreadyExistException;
import com.wu.accountservice.exception.ResourceNotFoundException;
import com.wu.accountservice.payload.ApiResponse;
import com.wu.accountservice.repository.AccountRepository;
import com.wu.accountservice.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    //create account
    @Override
    public ApiResponse createAccount(Account account) {
    try{
       String randomId = UUID.randomUUID().toString();
       account.setAccountId(randomId);
       Account existingAccount = accountRepository.findByAccountNo(account.getAccountNo());
        if (existingAccount != null) {
            logger.error("Error While creating account{}");
            throw new AlreadyExistException("An account with the same account number already exists");
        }
        logger.info("Account Created Successfully{}");
        accountRepository.save(account);
        return new ApiResponse("Account created Successfully",true);

    } 
    catch(Exception e){
        logger.error("Error while creating account: " + e.getMessage(), e);
        return new ApiResponse("An unexpected error occurred while creating the account", false);
    }
    }

    
    //get account details for user
    @Override
    public List<Account> get(String userId) {
        
        List<Account>account= accountRepository.getByUserId(userId);
        if(account==null){
            logger.error("Account not found{} ");
            throw new ResourceNotFoundException(" Account not found ");
        }
        logger.info("Account Found for given user :",userId);
        return account;
    }

    //get balance 
    @Override
    public Double getBalance(String accountId) {
       Account account=accountRepository.findByAccountId(accountId);
        if(account==null){
            logger.error("Account not found ");
            throw new ResourceNotFoundException("Account not found ");
        }
        logger.info("got account balance for account:{}",accountId);
       return account.getBalance();
    }

    //update 
    @Override
    public ApiResponse updateBalance(UpdateRequest updateRequest) {
        Account account=accountRepository.findByAccountId(updateRequest.getAccountId());
        if(account==null){
            logger.error("Account not found");
            throw new ResourceNotFoundException("Account not found");
        }
        account.setBalance(updateRequest.getAmount());
        accountRepository.save(account);
        return new ApiResponse("Account Balance Updated Successfully",true);
    }


    @Override
    public ApiResponse updateAccountDetails(String userId, Account account) {
        account.setUserId(userId);
        List<Account> accounts=accountRepository.getByUserId(userId);

        if(!accounts.isEmpty()){
            account.setAccountId(accounts.get(0).getAccountId());
            accountRepository.save(account);
            logger.info("Account details updated successfully.");
            return new ApiResponse("Account details Updated Successfully",true);
        }
        String randomId = UUID.randomUUID().toString();
        account.setAccountId(randomId);
        accountRepository.save(account);
        logger.info("Account created successfully.");
        return new ApiResponse("Account created Successfully",true);

    }


    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }


    @Override
    public List<Account> getAllExcept(String accountId) {
        List<Account>accounts=accountRepository.findAll();
        List<Account>getAccountsExcept=new ArrayList<>();
        for(Account ac:accounts){
            if(!ac.getAccountId().equals(accountId)){
                getAccountsExcept.add(ac);
            }
        }
        return getAccountsExcept;
    }


    @Override
    public Account getByAccountId(String accountId) {
      return accountRepository.findByAccountId(accountId);
    }
   

    
}
