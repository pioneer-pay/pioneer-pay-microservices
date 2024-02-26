package com.wu.userservice.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wu.userservice.entity.Account;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountFeignClient {

    @GetMapping("api/account/{userId}")
    List<Account> getAccountDetails(@PathVariable String userId);
}

