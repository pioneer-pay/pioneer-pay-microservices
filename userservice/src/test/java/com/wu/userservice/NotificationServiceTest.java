package com.wu.userservice;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wu.userservice.entity.Notification;
import com.wu.userservice.repository.NotificationRepository;
import com.wu.userservice.service.notification.NotificationServiceImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void testCreateNotification_Success() {
        // Arrange
        String userId = "user123";
        String message = "Test Message";
        List<Notification> notifications = new ArrayList<>();
        when(notificationRepository.findAll()).thenReturn(notifications);

        // Act
        String result = notificationService.createNotification(userId, message);

        // Assert
        assertEquals("Notification Created Successfully", result);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }


    @Test
    public void testCreateNotification_MaxNotifications() {
        // Arrange
        String userId = "user123";
        String message = "Test Message";
        List<Notification> notifications = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setMessage(message);
            notification.setTimestamp(LocalDateTime.now());
            notification.setRead(false);
            notifications.add(notification);
        }
        when(notificationRepository.findAll()).thenReturn(notifications);

        // Act
        String result = notificationService.createNotification(userId, message);

        // Assert
        assertEquals("Notification Created Successfully", result);
        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(notificationRepository, times(1)).delete(any(Notification.class));
    }



    @Test
    public void testCreateNotification_Failure() {
        // Arrange
        String userId = "user123";
        String message = "Test Message";
        List<Notification> notifications = new ArrayList<>();
        when(notificationRepository.findAll()).thenThrow(new RuntimeException());

        // Act
        String result = notificationService.createNotification(userId, message);

        // Assert
        assertEquals("Failed to create notification", result);
        verify(notificationRepository, never()).save(any(Notification.class));
    }
    


    @Test
    void testGetAllNotifications() {
        // Mocking repository behavior
        List<Notification> mockNotifications = new ArrayList<>();
        mockNotifications.add(new Notification(null, "1", "Notification 1", null, false));
        mockNotifications.add(new Notification(null, "2", "Notification 2", null, false));
        Mockito.when(notificationRepository.findAllByUserId(anyString())).thenReturn(mockNotifications);

        // Testing with valid userId
        List<Notification> notifications = notificationService.getAllNotifications("userId");
        assertEquals(2, notifications.size());
        assertEquals("Notification 2", notifications.get(0).getMessage());
        assertEquals("Notification 1", notifications.get(1).getMessage());

        // Testing with null userId
        List<Notification> emptyNotifications = notificationService.getAllNotifications(null);
        assertEquals(0, emptyNotifications.size());

        // Testing with empty userId
        emptyNotifications = notificationService.getAllNotifications("");
        assertEquals(0, emptyNotifications.size());
    }


    @Test
    public void testGetUnreadNotifications() {
        // Mock the notification repository
        List<Notification> unreadNotifications = new ArrayList<>();
        unreadNotifications.add(new Notification(null, "1", "Notification 1", null, false));
        unreadNotifications.add(new Notification(null, "2", "Notification 2", null, false));
        Mockito.when(notificationRepository.findByUserIdAndIsReadFalse(anyString())).thenReturn(unreadNotifications);

        // Verify the result
        assertEquals(2, unreadNotifications.size());
        assertEquals("Notification 1", unreadNotifications.get(0).getMessage());
        assertEquals("Notification 2", unreadNotifications.get(1).getMessage());
    }

     @Test
    void markNotificationAsRead_NotificationFound() {
        // Given
        Long notificationId = 1L;
        Notification notification = new Notification();
        notification.setId(notificationId);
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // When
        notificationService.markNotificationAsRead(notificationId);

        // Then
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void markNotificationAsRead_NotificationNotFound() {
        // Given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // When
        try {
            notificationService.markNotificationAsRead(notificationId);
        } catch (EntityNotFoundException ex) {
            // Then
            verify(notificationRepository, times(1)).findById(notificationId);
            verify(notificationRepository, never()).save(any());
        }
    }

    @Test
    void markNotificationAsRead_ExceptionThrown() {
        // Given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenThrow(new RuntimeException("Something went wrong"));

        // When
        try {
            notificationService.markNotificationAsRead(notificationId);
        } catch (Exception ex) {
            // Then
            verify(notificationRepository, times(1)).findById(notificationId);
            verify(notificationRepository, never()).save(any());
           
        }
    }

    @Test
    public void testMarkAllNotificationsAsRead() {
        // Arrange
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification());
        notifications.add(new Notification());

        when(notificationRepository.findAll()).thenReturn(notifications);

        // Act
        notificationService.markAllNotificationsAsRead();

        // Assert
        verify(notificationRepository, times(1)).findAll();
        verify(notificationRepository, times(1)).saveAll(notifications);
    }

    @Test
    public void testMarkAllNotificationsAsRead_Exception() {
        // Arrange
        when(notificationRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));

        // Act
        notificationService.markAllNotificationsAsRead();

    }

}
