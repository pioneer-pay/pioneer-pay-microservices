package com.wu.transaction;
import com.wu.transaction.entity.SendMoneyReminder;
import com.wu.transaction.payload.ApiResponse;
import com.wu.transaction.repository.SendMoneyReminderRepository;
import com.wu.transaction.service.ReminderSchedulingService;
import com.wu.transaction.service.impl.TransactionServiceImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDate;
import java.sql.Date;
import java.time.ZoneId;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.logging.log4j.Logger;
// import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveReminderServiceTest {

    @Autowired
    private TransactionServiceImpl reminderService;

    @MockBean
    private SendMoneyReminderRepository sendMoneyReminderRepository;

    @MockBean
    private ReminderSchedulingService reminderSchedulingService;

    @Mock
    private Logger logger;

    @Test
    public void testSaveReminder_Success() throws Exception {

        // Create a mock SendMoneyReminder object
        SendMoneyReminder reminder = new SendMoneyReminder();
        reminder.setAmount(100.0);
        reminder.setSourceCountry("Country1");
        reminder.setDestinationCountry("Country2");
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
        // Set other required fields (sourceCountry, destinationCountry, amount, etc.)

        // reminder.setDate(LocalDate.now()); // Set date (assuming required by service)
        // reminder.setTime(LocalTime.now()); 
        // Mock repository behavior to return the saved reminder
        SendMoneyReminder savedReminder = new SendMoneyReminder(); // Create a mock saved reminder
        savedReminder.setReminderId(1L); // Set a mock ID
        Mockito.when(sendMoneyReminderRepository.save(reminder)).thenReturn(savedReminder);

        // Call the service method
        ApiResponse response = reminderService.saveReminder(reminder);

        // Assertions
        assertEquals("Reminder Created!", response.getMessage());
        assertTrue(response.getStatus());
        Mockito.verify(sendMoneyReminderRepository).save(reminder);
        //Mockito.verify(reminderSchedulingService).scheduleReminderIfNeeded(savedReminder);
    }


    @Test
    public void testSaveReminder_ThrowsRuntimeException() throws Exception {
    // Create a mock SendMoneyReminder object
    SendMoneyReminder reminder = new SendMoneyReminder();
    // Set required fields
    reminder.setAmount(100.0);
    reminder.setSourceCountry("Country1");
    reminder.setDestinationCountry("Country2");

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

    // Mock repository behavior to throw RuntimeException
    Mockito.when(sendMoneyReminderRepository.save(reminder)).thenThrow(new RuntimeException("Database error"));

    // Call the service method
    ApiResponse response = reminderService.saveReminder(reminder);

    // Assertions
    assertEquals("Failed to create reminder due to database error", response.getMessage());
    assertFalse(response.getStatus());
    Mockito.verify(sendMoneyReminderRepository).save(reminder);
    Mockito.verifyNoInteractions(reminderSchedulingService); // Scheduling not called on error
}



    @Test
    public void testSaveReminder_UnexpectedError() throws Exception {

        // Create a mock SendMoneyReminder object
        SendMoneyReminder reminder = new SendMoneyReminder();
        // Set required fields
         // Set required fields
    reminder.setAmount(100.0);
    reminder.setSourceCountry("Country1");
    reminder.setDestinationCountry("Country2");

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


        // Mock repository behavior to throw a generic Exception
       // Mockito.when(sendMoneyReminderRepository.save(reminder)).thenThrow(new Exception("Unexpected error"));
       Mockito.when(sendMoneyReminderRepository.save(reminder)).thenThrow(new RuntimeException("Unexpected error"));

        // Call the service method
        ApiResponse response = reminderService.saveReminder(reminder);

        // Assertions
        

        assertFalse(response.getStatus());
        Mockito.verify(sendMoneyReminderRepository).save(reminder);
       // Mockito.verifyNoInteractions(reminderSchedulingService); // Scheduling not called on error
    }
}
