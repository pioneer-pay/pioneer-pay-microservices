// package com.wu.transaction;

// import static org.junit.Assert.*;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import com.wu.transaction.entity.Email;
// import com.wu.transaction.entity.Transaction;
// import com.wu.transaction.entity.dao.NotificationRequest;
// import com.wu.transaction.external.AccountFeignClient;
// import com.wu.transaction.external.UserFeignClient;
// import com.wu.transaction.payload.ApiResponse;
// import com.wu.transaction.repository.TransactionRepository;
// import com.wu.transaction.service.emailService.EmailService;
// import com.wu.transaction.service.exchnageRate.CurrencyService;
// import com.wu.transaction.service.exchnageRate.ExchangeService;
// import com.wu.transaction.service.impl.TransactionServiceImpl;


// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// public class TransactionServiceTest {

//     @Mock
//     private TransactionRepository transactionRepository;

//     @MockBean
//     private AccountFeignClient accountFeignClient;

//     @MockBean
//     private UserFeignClient userFeignClient;

//     @Mock
//     private EmailService emailService;

//     @Mock
//     private CurrencyService currencyService;

//     @Mock
//     private ExchangeService exchangeService;

//     @InjectMocks
//     private TransactionServiceImpl transactionService;

//     @Test
//     public void testInitiateTransferSuccess() {
//     // Mock transaction object
//     Transaction transaction = new Transaction();
//     transaction.setFromAccountId("fromAccount");
//     transaction.setToAccountId("toACcount");
//     transaction.setBaseCurrencyCode("USD");
//     transaction.setTargetCurrencyCode("EUR");
//     transaction.setAmount(100.0);

//     // Mock accountFeignClient and userFeignClient behavior (using Mockito)
//     Mockito.when(accountFeignClient.getUserIdByAccountId(transaction.getFromAccountId())).thenReturn("user1");
//     Mockito.when(userFeignClient.getEmailByUserId("user1")).thenReturn("user1@example.com");

//     // Call the method
//     ApiResponse apiResponse = transactionService.initiateTransfer(transaction);

//     // Assert response
//     assertEquals(apiResponse.getMessage(), "Transaction Initiated..");
//     assertEquals(apiResponse.getStatus(), true);

//     // Verify email and notification calls
//     Mockito.verify(emailService, Mockito.times(1)).sendEmail("user1@example.com", Mockito.any(Email.class));
//     Mockito.verify(userFeignClient, Mockito.times(1)).createNotification(Mockito.any(NotificationRequest.class));
//     }

//     @Test
//     public void testInitiateTransferInsufficientBalance() throws Exception {
//     // Mock transaction object with insufficient balance
//     Transaction transaction = new Transaction();
//     transaction.setFromAccountId("fromAccount");
//     transaction.setToAccountId("toAccount");
//     transaction.setBaseCurrencyCode("USD");
//     transaction.setTargetCurrencyCode("EUR");
//     transaction.setAmount(1000.0);

//     // Mock accountFeignClient to throw exception for getBalance
//     Mockito.when(accountFeignClient.getBalance(transaction.getFromAccountId())).thenThrow(new Exception("Insufficient balance"));
//     try {
//         transactionService.initiateTransfer(transaction);
//         fail("Expected exception not thrown"); // This line will fail if no exception is thrown
//       } catch (RuntimeException e) {
//         // Assert the exception message
//         assertTrue(e.getMessage().contains("Insufficient balance"));
//       }

//     }
// }
