package com.wu.transaction.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="USER-INFO-SERVICE")
public interface UserFeignClient {
    @GetMapping("/api/user/get/email/{userId}")
    public String getEmailByUserId(@PathVariable String userId);
}
