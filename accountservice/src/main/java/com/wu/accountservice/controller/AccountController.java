package com.wu.accountservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wu.accountservice.entity.Account;
import com.wu.accountservice.entity.UpdateRequest;
import com.wu.accountservice.payload.ApiResponse;
import com.wu.accountservice.service.AccountService;


@RestController
@RequestMapping("/api/account")
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    //create account
    @PostMapping("/add")
    public ResponseEntity<ApiResponse>createAccount(@RequestBody Account account){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
    }

    //get account details using userId
    @GetMapping("/{userId}")
    public ResponseEntity<List<Account>>getAccountForUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.get(userId));
    }
   
   //get balance
   @GetMapping("/balance/{accountId}")
   public  ResponseEntity<Double>getBalanceByAccountId(@PathVariable String accountId){
    return ResponseEntity.status(HttpStatus.OK).body(accountService.getBalance(accountId));
   }

    @GetMapping("/details/{accountId}")
  public ResponseEntity<Account>getAccountDetailsByAccountId(@PathVariable String accountId){
    return ResponseEntity.status(HttpStatus.OK).body(accountService.getByAccountId(accountId));
  }
   

   //update account balance
   @PutMapping("/update")
   public ResponseEntity<ApiResponse>updateBalance(@RequestBody UpdateRequest updateRequest){
    return ResponseEntity.status(HttpStatus.OK).body(accountService.updateBalance(updateRequest));
   }

   //update account details
   @PutMapping("/update/{userId}")
   public ResponseEntity<ApiResponse>updateAccountDetails(@PathVariable String userId,@RequestBody Account account){
    return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccountDetails(userId,account));
   }


   //get all 
   @GetMapping("/get")
   public ResponseEntity<List<Account>>getAllAccounts(){
    return ResponseEntity.status(HttpStatus.OK).body(accountService.getAll());
   }

    @GetMapping("/get/{accountId}")
   public ResponseEntity<List<Account>>getAllAccountsExcept(@PathVariable String accountId){
    return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllExcept(accountId));
   }
}
