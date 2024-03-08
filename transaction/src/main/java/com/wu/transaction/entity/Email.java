package com.wu.transaction.entity;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String subject;
    // private String toEmail;
    private String message;
    // private LocalDateTime scheduledSendTime;
    // private Boolean sent = false;
}
