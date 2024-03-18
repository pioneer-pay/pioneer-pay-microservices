package com.wu.transaction;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.wu.transaction.entity.Email;
import com.wu.transaction.service.emailService.EmailService;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    
    @SuppressWarnings("null")
    @Test
    void testSendEmail_Success() {
        // Arrange
        Email email = new Email("Test Subject", "Test Message");
        String to = "recipient@example.com";

        emailService.sendEmail(to, email);

        // Assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }


    @SuppressWarnings("null")
    @Test
    void testSendEmail_Failure() {
        // Arrange
        Email email = new Email("Test Subject", "Test Message");
        String to = "recipient@example.com";
        doThrow(new MailSendException("Test Exception")).when(javaMailSender).send(any(SimpleMailMessage.class));
        // Act
        emailService.sendEmail(to, email);
    }
}
