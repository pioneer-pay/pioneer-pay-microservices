package com.wu.transaction.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "send_money_reminder")
@EntityListeners(AuditingEntityListener.class)
public class SendMoneyReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderId;
    private String createdBy;
    private String modifiedBy;
 
    @LastModifiedDate
    private LocalDateTime modifiedAt;
 
    @CreatedDate
    private LocalDateTime createdAt;
   
    private Date date;
    private Time time;
    // private String combineddatetime;
    private LocalDateTime datetime;
 
    private String sourceCountry;
    private String destinationCountry;
    private String payOut;
    private Double amount;
   
    @Enumerated(EnumType.STRING)
    private PaymentMethod payIn;
 
    @Enumerated(EnumType.STRING)
    private ReminderStatus status;
}