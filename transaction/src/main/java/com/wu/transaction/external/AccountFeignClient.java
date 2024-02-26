package com.wu.transaction.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wu.transaction.entity.UpdateBalance;
import com.wu.transaction.payload.ApiResponse;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountFeignClient {
    @GetMapping("/api/account/balance/{accountId}")
    public Double getBalance(@PathVariable String accountId);

    @PutMapping("/api/account/update")
    public ApiResponse updateBalance(@RequestBody UpdateBalance updateBalance);

    

    
}
