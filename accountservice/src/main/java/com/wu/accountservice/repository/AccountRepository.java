package com.wu.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wu.accountservice.entity.Account;





public interface AccountRepository extends JpaRepository<Account,String> {

    List<Account> getByUserId(String userId);

    Account findByAccountId(String accountId);

    Account findByAccountNo(String accountNo);
}
