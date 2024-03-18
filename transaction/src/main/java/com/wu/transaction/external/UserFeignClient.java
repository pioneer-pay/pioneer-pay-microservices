package com.wu.transaction.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wu.transaction.entity.dao.NotificationRequest;

@FeignClient(name="USER-INFO-SERVICE")
public interface UserFeignClient {
    @GetMapping("/api/user/get/email/{userId}")
    public String getEmailByUserId(@PathVariable String userId);
    
    
    @PostMapping("/api/notification/create")
    public String createNotification(@RequestBody NotificationRequest notificationRequest);
}
