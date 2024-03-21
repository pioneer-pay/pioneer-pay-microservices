package com.wu.accountservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wu.accountservice.entity.dao.NotificationRequest;

@FeignClient(name="USER-INFO-SERVICE")
public interface UserFeignClient {
   
    @PostMapping("/api/notification/create")
    public String createNotification(@RequestBody NotificationRequest notificationRequest);
}

