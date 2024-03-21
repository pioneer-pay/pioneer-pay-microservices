package com.wu.transaction.service.emailService;
 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
 
import com.wu.transaction.entity.Email;
import com.wu.transaction.service.impl.TransactionServiceImpl;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
 
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
 
    @Value("${spring.mail.username}")
    private String senderMail;
 
    public void sendEmail(String to,Email email){
 
        try{
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom(senderMail);
            simpleMailMessage.setSubject(email.getSubject());
            simpleMailMessage.setText(email.getMessage());
            simpleMailMessage.setTo(to);
            javaMailSender.send(simpleMailMessage);
            logger.info("Email sent Successfully!!");
        }
        catch (MailException e) {
        logger.error("Failed to send email to {}: {}", to, e.getMessage());
        e.printStackTrace();
    }
    }
}
 