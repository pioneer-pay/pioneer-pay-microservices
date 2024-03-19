package com.wu.transaction.entity;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.wu.transaction.entity.PaymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="send_money_reminder")
public class SendMoneyReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderId;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime modifiedAt; 
    private LocalDateTime createdAt;
    private Date date;
    private Time time;
    private String sourceCountry;
    private String destinationCountry;    
    private String payOut;
    private Double amount;
   
    
    
    @Enumerated(EnumType.STRING)
    private PaymentMethod payIn;


    @Enumerated(EnumType.STRING)
    private ReminderStatus status; // Added status field
}
