//package com.wu.transaction;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import com.wu.transaction.entity.Transaction;
//import com.wu.transaction.external.AccountFeignClient;
//import com.wu.transaction.payload.ApiResponse;
//import com.wu.transaction.repository.TransactionRepository;
//import com.wu.transaction.service.TransactionService;
//import com.wu.transaction.service.exchnageRate.CurrencyService;
//import com.wu.transaction.service.exchnageRate.ExchangeService;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@TestPropertySource("classpath:application-test.properties")
//public class transactionServiceTest {
//   @InjectMocks
//    private TransactionService transactionService;
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private AccountFeignClient accountFeignClient;
//
//    @Mock
//    private CurrencyService currencyService;
//
//    @Mock
//    private ExchangeService exchangeService;
//
//    @Test
//    public void testInitiateTransfer() {
//        // Arrange
//        Transaction transaction = new Transaction();  // Create a transaction
//        transaction.setTransactionId(123L);
//        transaction.setBaseCurrencyCode("USD");
//        transaction.setTargetCurrencyCode("INR");
//        transaction.setStatus("PENDING");
//        // Act
//        ApiResponse response = transactionService.initiateTransfer(transaction);
//
//        // Assert
//        verify(transactionRepository, times(1)).save(transaction);
//
//        assertEquals("Transaction Initiated..", response.getMessage());
//        assertTrue(response.getStatus());
//    }
//
//   @Test
//    public void testCompleteTransfer() throws Exception {
//        // Arrange
//        Long transactionId = 123L;
//        Transaction transaction = new Transaction();  // Create a transaction for the given transaction ID
//        // Set up mock behavior for accountFeignClient, currencyService, and exchangeService as needed
//
//        when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transaction);
//        when(accountFeignClient.getBalance(anyString())).thenReturn(100.0);  // Mock the balance retrieval
//        when(currencyService.getFeeByCode(anyString())).thenReturn(0.5);  // Mock the fee retrieval
//        when(exchangeService.convertCurrency(anyString(), anyString(), anyDouble())).thenReturn(75.0);  // Mock the currency conversion
//
//        // Act
//        ApiResponse response = transactionService.completeTransfer(transactionId);
//
//    }
//
//    @Test
//    public void testGetTransactionHistoryByAccountId() {
//        // Arrange
//        String accountId = "account123";
//
//        // Mock the behavior of transactionRepository.findByFromAccountIdOrToAccountId()
//        List<Transaction> mockTransactionList = new ArrayList<>();  // Create a mock list of transactions
//        when(transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId)).thenReturn(mockTransactionList);
//
//        // Act
//        List<Transaction> transactionHistory = transactionService.getTransactionHistoryByAccountId(accountId);
//        assertEquals(mockTransactionList, transactionHistory);
//    }
//
//}