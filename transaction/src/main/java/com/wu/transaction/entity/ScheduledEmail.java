package com.wu.transaction.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// @Entity
// @Table(name = "scheduled_email")
public class ScheduledEmail{
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toEmail;
    private String subject;
    private String message;
    private LocalDateTime scheduledSendTime;
    private Boolean sent = false;
}
