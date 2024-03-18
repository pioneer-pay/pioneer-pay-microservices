package com.wu.userservice.service.notification;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wu.userservice.entity.Notification;
import com.wu.userservice.repository.NotificationRepository;
import com.wu.userservice.service.impl.UserServiceImpl;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    //create notification
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
            logger.info("notification created successfully!!");
            return "Notification Created Successfully";

        } catch (Exception e) {
            logger.error("Failed to create notification", e);
            return "Failed to create notification"; 
        }
    }


    //get all unread notifications
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    //get all notifications
    public List<Notification> getAllNotifications(String userId){
        try {
            if (userId == null || userId.isEmpty()) {
                throw new IllegalArgumentException("User ID cannot be null or empty");
            }
            List<Notification> notifications = notificationRepository.findAllByUserId(userId);
            if (notifications == null || notifications.isEmpty()) {
                return Collections.emptyList();
            }

            Collections.reverse(notifications);
            return notifications;
        } catch (Exception e) {
            e.printStackTrace(); 
            return Collections.emptyList();
        }
    }
    

}

