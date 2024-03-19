package com.wu.transaction.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wu.transaction.entity.PaymentMethod;
import com.wu.transaction.entity.ReminderStatus;
import com.wu.transaction.entity.SendMoneyReminder;
import com.wu.transaction.entity.Email;
import com.wu.transaction.entity.Summary;
import com.wu.transaction.entity.Transaction;
import com.wu.transaction.entity.UpdateBalance;
import com.wu.transaction.entity.dao.NotificationRequest;
import com.wu.transaction.external.AccountFeignClient;
import com.wu.transaction.external.UserFeignClient;
import com.wu.transaction.payload.ApiResponse;
import com.wu.transaction.repository.TransactionRepository;
import com.wu.transaction.repository.SendMoneyReminderRepository;
import com.wu.transaction.service.TransactionService;
import com.wu.transaction.service.emailService.EmailService;
import com.wu.transaction.service.exchnageRate.CurrencyService;
import com.wu.transaction.service.exchnageRate.ExchangeService;
import com.wu.transaction.entity.Email;
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SendMoneyReminderRepository sendMoneyReminderRepository;
    
    @Autowired
    private ExchangeService exchangeService;


    private final AccountFeignClient accountFeignClient;

    public TransactionServiceImpl(AccountFeignClient accountFeignClient) {
        this.accountFeignClient = accountFeignClient;
    }


    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailService emailService;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    //Initiate transfer
    @Override
    public ApiResponse initiateTransfer(Transaction transaction) {

        transaction.setBaseCurrencyCode(transaction.getBaseCurrencyCode());
        transaction.setTargetCurrencyCode(transaction.getTargetCurrencyCode());
        transaction.setStatus("PENDING");
        transactionRepository.save(transaction);

        //send an email on initiate transfer
        Email email=new Email();
        email.setSubject("Transaction Status");
        email.setMessage("Hi,\n\nYour Transaction Initiated ,Please wait for the successful completion of the transaction.\n\n\nThank You");
        String userId=accountFeignClient.getUserIdByAccountId(transaction.getFromAccountId());
        String sendTo=userFeignClient.getEmailByUserId(userId);
        emailService.sendEmail(sendTo,email);
        logger.info("successfully sent an email after initiate transfer!!");


        //send notification initiate transfer
        String notificationMessage="Transaction Initiated ,Please wait for the successful completion of the transaction.";
        NotificationRequest notificationRequest=new NotificationRequest();
        notificationRequest.setMessage(notificationMessage);
        notificationRequest.setUserId(userId);
        userFeignClient.createNotification(notificationRequest);
        logger.info("Notification addedd successfully!");
        logger.info("Transaction Initiated ,Please wait for the successful completion of the transaction.");
        return new ApiResponse("Transaction Initiated..",true);

    }
 
  @Override
    public ApiResponse saveReminder(SendMoneyReminder sendMoneyReminder) {
        try {
            LocalDateTime reminderDateTime = LocalDateTime.of(sendMoneyReminder.getDate().toLocalDate(), sendMoneyReminder.getTime().toLocalTime());
            sendMoneyReminder.setPayOut("BANK");
            sendMoneyReminder.setStatus(ReminderStatus.PENDING);
            sendMoneyReminder.setDatetime(reminderDateTime);
            SendMoneyReminder savedReminder=sendMoneyReminderRepository.save(sendMoneyReminder);
            logger.info("Successfully saved the reminder to the database");
            
            //Schedule the reminder for sending an email
            // reminderSchedulingService.scheduleReminderIfNeeded(savedReminder);
            
            return new ApiResponse("Reminder Created!", true);
        } catch (RuntimeException e) {
            // indicates a database error (e.g., SQLException)
            logger.error("Error while saving the reminder to the database", e);
            return new ApiResponse("Failed to create reminder due to database error", false);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while saving the reminder", e);
            // Return or throw a different ApiResponse or custom exception for unexpected errors
            return new ApiResponse("An unexpected error occurred", false);
        }
    }

    //completion of transfer
    @Override
    public ApiResponse completeTransfer(Long transactionId) throws Exception {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        try {
            Double senderBalance = accountFeignClient.getBalance(transaction.getFromAccountId());
            Double receiverBalance = accountFeignClient.getBalance(transaction.getToAccountId());
            Double feeAmount=0.0;

             Double fee = currencyService.getFeeByCode(transaction.getBaseCurrencyCode());
             feeAmount = (transaction.getAmount() * fee) / 100;

            Double transactionAmountAfterFee = transaction.getAmount() - feeAmount;
            transaction.setCommission(feeAmount);
    
            double convertedAmount = exchangeService.convertCurrency(transaction.getBaseCurrencyCode(), transaction.getTargetCurrencyCode(), transactionAmountAfterFee);
    
            if (senderBalance < transaction.getAmount()) {
                logger.error("Insufficient balance{}");
                transaction.setStatus("FAILED");
                transaction.setDateTime(LocalDateTime.now());
                transactionRepository.save(transaction);

                //send an email on Failed transfer
                Email email=new Email();
                email.setSubject("Transaction Status");
                email.setMessage("Hi,\n\nYour Transaction got FAILED,Please check your account balance and try again.\n\n\nThank You");
                String userId=accountFeignClient.getUserIdByAccountId(transaction.getFromAccountId());
                String sendTo=userFeignClient.getEmailByUserId(userId);
                emailService.sendEmail(sendTo,email);


                //send notification on Failed Transfer
                String notificationMessage="Your transaction is FAILES,Please check your account balance";
                NotificationRequest notificationRequest=new NotificationRequest();
                notificationRequest.setMessage(notificationMessage);
                notificationRequest.setUserId(userId);
                userFeignClient.createNotification(notificationRequest);
                logger.info("Notification added successfully!");


                logger.info("Email sent after transaction got FAILED");
                return new ApiResponse("Insufficient balance", false);
            }
    
            UpdateBalance updateSenderBalance = new UpdateBalance(transaction.getFromAccountId(), senderBalance - transaction.getAmount());
            transaction.setTransferedAmount(convertedAmount);
            UpdateBalance updateReceiverBalance = new UpdateBalance(transaction.getToAccountId(), receiverBalance + convertedAmount);
            accountFeignClient.updateBalance(updateSenderBalance);
            accountFeignClient.updateBalance(updateReceiverBalance);
    
            transaction.setDateTime(LocalDateTime.now());
            transaction.setStatus("SUCCESS");
            transactionRepository.save(transaction);

            //send an email on Successful Transfer
            Email email=new Email();
            email.setSubject("Transaction Status");
            email.setMessage("Hi,\n\nYour Transaction is Successful!!\n\n\nThank You.");
            String userId=accountFeignClient.getUserIdByAccountId(transaction.getFromAccountId());
            String sendTo=userFeignClient.getEmailByUserId(userId);
            emailService.sendEmail(sendTo,email);
            logger.info("Email sent successfully after successful transaction.");


            //send notification on Successful Transfer
            String notificationMessage="Your transaction is Successful!!";
            NotificationRequest notificationRequest=new NotificationRequest();
            notificationRequest.setMessage(notificationMessage);
            notificationRequest.setUserId(userId);
            userFeignClient.createNotification(notificationRequest);
            logger.info("Notification added successfully!");
            

            logger.info("Transaction successful!!");
            return new ApiResponse("Transaction Successful", true);
        }catch (Exception e) {
            logger.error("An error occurred during the transaction: {}", e.getMessage());
            transaction.setStatus("FAILED");
            return new ApiResponse("An error occurred during the transaction", false);
        }
    }
    
    

    //Transaction History of User
    @Override
    public List<Transaction> getTransactionHistoryByAccountId(String accountId) {
        try {
            List<Transaction> list = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
            Collections.reverse(list);
            if (list.isEmpty()) {
                logger.info("Transaction history not found");
            }
            logger.info("Transaction History for :{}", accountId);
            return list;
        }catch (Exception e) {
            logger.error("Error occurred while fetching transaction history: {}", e.getMessage());
            return Collections.emptyList(); 
        }
    
    }


    //transaction summary with exchange rate.
    @Override
    public Summary getSummary(String baseCurrencyCode, String targetCurrencyCode, Double amount) {
        Transaction transaction= new Transaction();
        Summary summary=new Summary();
        transaction.setBaseCurrencyCode(baseCurrencyCode);
        summary.setBaseCurrencyCode(baseCurrencyCode);
        transaction.setTargetCurrencyCode(targetCurrencyCode);
        summary.setTargetCurrencyCode(targetCurrencyCode);
        transaction.setAmount(amount);
        summary.setAmount(amount);
        Double fee= currencyService.getFeeByCode(baseCurrencyCode);
        Double commission= amount*fee/100;
        transaction.setCommission(commission);
        summary.setCommission(commission);
        Double transferAmountAfterfee=  transaction.getAmount() - (transaction.getAmount()*fee/100);
        Double receiveAmount=0.0;
        Double rate=0.0;
        try {
            receiveAmount = exchangeService.convertCurrency(transaction.getBaseCurrencyCode(),transaction.getTargetCurrencyCode() , transferAmountAfterfee);
            rate=receiveAmount/transferAmountAfterfee;
        } catch (Exception e) {
            e.printStackTrace();
        }
         transaction.setTransferedAmount(receiveAmount);
         summary.setReceivedMoney(receiveAmount);
         summary.setRate(rate);
         return summary;
    }
    
}
