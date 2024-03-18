package com.wu.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wu.userservice.entity.Notification;
import com.wu.userservice.entity.NotificationRequest;
import com.wu.userservice.service.notification.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    
    //create notification
    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequest notificationRequest) {
       return ResponseEntity.status(HttpStatus.OK).body(notificationService.createNotification(notificationRequest.getUserId(),notificationRequest.getMessage()));
    }
    
    //get unread notifications
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotificationsForUser(@PathVariable String userId) {
      
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getUnreadNotifications(userId));
    }
    
    //get all notifications
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Notification>> getAllNotifications(@PathVariable String userId){
       return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllNotifications(userId));
    }
    
}
