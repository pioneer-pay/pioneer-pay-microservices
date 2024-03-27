package com.wu.transaction.service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.wu.transaction.entity.ReminderStatus;
import com.wu.transaction.entity.SendMoneyReminder;
import com.wu.transaction.repository.SendMoneyReminderRepository;




@Service
public class ReminderSchedulingService {
    
    @Autowired
    private SendMoneyReminderRepository reminderRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private SchedulerEmailService schedulerEmailService;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ReminderSchedulingService.class);


    @PostConstruct
    public void scheduleRemindersFromDatabase() {
       try {
            List<SendMoneyReminder> reminders = reminderRepository.findAll();
            for (SendMoneyReminder reminder : reminders) {
                scheduleReminderIfNeeded(reminder);
            }
        } catch (RuntimeException e) {
            logger.error("Error accessing reminder data from database", e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while scheduling reminders", e);
        }
    }

    public void scheduleReminderIfNeeded(SendMoneyReminder reminder) {
        try{
            logger.info("Schedule Reminder Service is called");
           LocalDateTime reminderDateTime =reminder.getDatetime();
        if (reminderDateTime.isAfter(LocalDateTime.now())) { // Check if the reminder is in the future
            Instant reminderInstant = reminderDateTime.atZone(ZoneId.systemDefault()).toInstant();
            taskScheduler.schedule(() -> {
                sendEmailReminder(reminder);
            }, Date.from(reminderInstant));
            logger.info("Scheduled the reminder at {}", reminderDateTime);
            
        }
        }catch (Exception e) {
            logger.error("Failed to schedule reminder for {}", reminder, e);
        }    
    }

    public void sendEmailReminder(SendMoneyReminder reminder) {
        try{
            // Construct your email message based on the reminder details
            String subject = "Reminder: Send Money";
            String body = String.format("Don't forget to send $%.2f from %s to %s via %s%nClick on the below link:", reminder.getAmount(),reminder.getSourceCountry(),reminder.getDestinationCountry(),reminder.getPayIn());
            schedulerEmailService.sendEmail(reminder.getCreatedBy(), subject, body);
            
            // Optionally update the reminder status in the database to indicate the email has been sent
            reminder.setStatus(ReminderStatus.SENT); // Assuming you have a SENT status
            reminderRepository.save(reminder);
        }catch (RuntimeException e) {
            logger.error("Failed to update reminder status in the database", e);
        } catch (Exception e) {
            logger.error("Failed to send email reminder", e);
        }
        
    }
}


