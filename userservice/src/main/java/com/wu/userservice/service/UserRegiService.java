package com.wu.userservice.service;

import java.util.List;

import com.wu.userservice.entity.Account;
import com.wu.userservice.entity.Transaction;
import com.wu.userservice.entity.User;
import com.wu.userservice.payload.ApiResponse;

public interface UserRegiService {
     //register user
     ApiResponse saveUser(User user) ;

     //login User
     ApiResponse login(User user) ;

     ApiResponse updateUser(String userId,User user);

     User getUserDetails(String userId);

     List<User> getAll();

     List<Account> getAccountDetails(String userId);

     List<Transaction> showTransactions(String userId);
}
