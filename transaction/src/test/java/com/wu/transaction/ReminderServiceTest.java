package com.wu.transaction;
import com.wu.transaction.entity.SendMoneyReminder;
import com.wu.transaction.entity.PaymentMethod;
import com.wu.transaction.entity.ReminderStatus;
import com.wu.transaction.payload.ApiResponse;
import com.wu.transaction.repository.SendMoneyReminderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReminderServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SendMoneyReminderRepository repository;

    @Test
    public void testSaveReminder() {
        // Given
        SendMoneyReminder reminder = new SendMoneyReminder();
        reminder.setCreatedBy("testUser");
        reminder.setCreatedAt(LocalDateTime.now());
        reminder.setModifiedAt(LocalDateTime.now());
        reminder.setDate(Date.valueOf("2023-03-13"));
        reminder.setTime(Time.valueOf("10:00:00"));
        reminder.setSourceCountry("US");
        reminder.setDestinationCountry("IN");
        reminder.setPayOut("BANK");
        reminder.setAmount(100.0);
        reminder.setPayIn(PaymentMethod.BankTransfer);
        reminder.setStatus(ReminderStatus.PENDING);

        // When
        SendMoneyReminder savedReminder = repository.save(reminder);

        // Then
        assertThat(savedReminder).isNotNull();
        assertThat(savedReminder.getReminderId()).isNotNull();
        assertThat(savedReminder.getStatus()).isEqualTo(ReminderStatus.PENDING);
        assertThat(savedReminder.getSourceCountry()).isEqualTo("US");
        assertThat(savedReminder.getDestinationCountry()).isEqualTo("IN");
        assertThat(savedReminder.getAmount()).isEqualTo(100.0);
    }
}
