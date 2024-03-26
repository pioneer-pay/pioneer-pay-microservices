// // 




package com.wu.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TaskScheduler;

import com.wu.transaction.entity.ReminderStatus;
import com.wu.transaction.entity.SendMoneyReminder;
import com.wu.transaction.repository.SendMoneyReminderRepository;
import com.wu.transaction.service.ReminderSchedulingService;
import com.wu.transaction.service.SchedulerEmailService;

public class ReminderSchedulingServiceTest {

    @Mock
    private SendMoneyReminderRepository reminderRepository;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private SchedulerEmailService schedulerEmailService;

    @InjectMocks
    private ReminderSchedulingService reminderSchedulingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//  @Test
//     public void testScheduleReminderIfNeeded_futureReminder_schedulesTaskAndCallsEmailService() {
//         // Given
        
        
//         SendMoneyReminder reminder = new SendMoneyReminder();
//         // Your LocalDate
//         LocalDate localDate = LocalDate.of(2024, 3, 19);
//         // Convert LocalDate to java.sql.Date
//         Date sqlDate = Date.valueOf(localDate);
//         // Now you can use the setDate method without issue
//         reminder.setDate(sqlDate);
//         //reminder.setDate(LocalDate.of(2024, 3, 19));
//         LocalTime localTime = LocalTime.parse("15:32:13");
//         Time time = Time.valueOf(localTime);

//         reminder.setTime(time);
//         LocalDateTime reminderDateTime = LocalDateTime.of(reminder.getDate().toLocalDate(), reminder.getTime().toLocalTime());

//         reminder.setDatetime(reminderDateTime);


//         List<SendMoneyReminder> reminders = new ArrayList<>();
//         reminders.add(reminder);
//         when(reminderRepository.findAll()).thenReturn(reminders);

//         // When
//         reminderSchedulingService.scheduleRemindersFromDatabase();

//         // Mock TaskScheduler and define specific behavior
//         Instant scheduledTime = reminderDateTime.atZone(ZoneId.systemDefault()).toInstant();
//         verify(taskScheduler).schedule(() -> reminderSchedulingService.sendEmailReminder(reminder));
//         // Then
//          verify(taskScheduler).schedule(() -> reminderSchedulingService.sendEmailReminder(reminder));
//         verify(schedulerEmailService).sendEmail(anyString(), anyString(), anyString());
//     }


    @Test
    public void testScheduleReminderIfNeeded_pastReminder_doesNotScheduleReminder() {
        // Given
        SendMoneyReminder reminder = new SendMoneyReminder();
       // Your LocalDate
        LocalDate localDate = LocalDate.of(2024, 3, 19);
        // Convert LocalDate to java.sql.Date
        Date sqlDate = Date.valueOf(localDate);
        // Now you can use the setDate method without issue
        reminder.setDate(sqlDate);
        //reminder.setDate(LocalDate.of(2024, 3, 19));
        LocalTime localTime = LocalTime.parse("15:32:13");
        Time time = Time.valueOf(localTime);

        reminder.setTime(time);
        LocalDateTime reminderDateTime = LocalDateTime.of(reminder.getDate().toLocalDate(), reminder.getTime().toLocalTime());

        reminder.setDatetime(reminderDateTime);
        reminder.setDestinationCountry("Country");
        reminder.setCreatedBy("test@example.com");
        reminder.setStatus(ReminderStatus.PENDING);

        // When
        reminderSchedulingService.scheduleReminderIfNeeded(reminder);

        // Then
        verify(taskScheduler, never()).schedule(any(Runnable.class), any(Date.class));
    }
}


