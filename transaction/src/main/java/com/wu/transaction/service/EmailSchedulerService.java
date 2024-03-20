// package com.wu.transaction.service;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;

// import com.wu.transaction.entity.Email;
// import com.wu.transaction.entity.ScheduledEmail;
// import com.wu.transaction.repository.ScheduledEmailRepository;
// import com.wu.transaction.service.emailService.EmailService;



// public class EmailSchedulerService {
// @Autowired
// private ScheduledEmailRepository scheduledEmailRepository;


// @Autowired
// private EmailService emailService; 


//  // Run every minute (customize as needed)
//   public void sendScheduledEmails() {
//     List<ScheduledEmail> dueEmails = scheduledEmailRepository.findBySentFalseAndScheduledSendTimeBefore(LocalDateTime.now());

//     for (ScheduledEmail scheduledEmail : dueEmails) {
//         try {
//             Email email = new Email(scheduledEmail.getSubject(), scheduledEmail.getMessage());
//             emailService.sendEmail(scheduledEmail.getToEmail(), email);
//             scheduledEmail.setSent(true);
//             scheduledEmailRepository.save(scheduledEmail);
//             // Log success
//         } catch (Exception e) {
//             // Log failure
//         }
//     }
// }




// }
