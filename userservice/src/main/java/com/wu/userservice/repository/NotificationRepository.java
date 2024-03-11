package com.wu.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wu.userservice.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndIsReadFalse(String userId);
    List<Notification> findAllByUserId(String userId);

}

