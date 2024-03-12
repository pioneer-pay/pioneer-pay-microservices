package com.wu.userservice.service.notification;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wu.userservice.entity.Notification;
import com.wu.userservice.repository.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    

    //create notification
    @SuppressWarnings("null")
    public String createNotification(String userId, String message) {
        try {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setMessage(message);
            notification.setTimestamp(LocalDateTime.now());
            notification.setRead(false);
    
            List<Notification> notifications = notificationRepository.findAll();
            int totalNotifications = notifications.size();
            int maxNotifications = 25;
    
            if (totalNotifications >= maxNotifications) {
                Collections.sort(notifications, Comparator.comparing(Notification::getTimestamp));
    
                // Calculate number of notifications to delete
                int notificationsToDelete = totalNotifications - maxNotifications + 1;
    
                // Delete the oldest notifications
                for (int i = 0; i < notificationsToDelete; i++) {
                    notificationRepository.delete(notifications.get(i));
                }
            }
            notificationRepository.save(notification);
            return "Notification Created Successfully";
        } catch (Exception e) {
            // Log the error or handle it appropriately
            return "Failed to create notification"; 
        }
    }


    //get all unread notifications
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    //get all notifications
    public List<Notification> getAllNotifications(String userId){
        List<Notification>notifications=notificationRepository.findAllByUserId(userId);
        Collections.reverse(notifications);
        return notifications;
    }
    

}

