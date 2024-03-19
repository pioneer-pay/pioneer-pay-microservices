package com.wu.transaction.entity.dao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;

    private String userId;
    
    private String message;
    
    private LocalDateTime timestamp;
    
    private boolean isRead;
}