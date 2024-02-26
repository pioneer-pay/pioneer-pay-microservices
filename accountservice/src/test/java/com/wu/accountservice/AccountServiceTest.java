//package com.wu.accountservice;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import com.wu.accountservice.entity.Account;
//import com.wu.accountservice.entity.UpdateRequest;
//import com.wu.accountservice.exception.ResourceNotFoundException;
//import com.wu.accountservice.payload.ApiResponse;
//import com.wu.accountservice.repository.AccountRepository;
//import com.wu.accountservice.service.impl.AccountServiceImpl;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@TestPropertySource("classpath:application-test.properties")
//class AccountServiceTest {
//    @Mock
//    private AccountRepository accountRepository;
//
//    @InjectMocks
//    private AccountServiceImpl accountService;
//
//    @Mock
//    private Logger logger;
//
//    //create account
//    @Test
//    void testCreateAccount() {
//        Account account = new Account();
//        account.setAccountNo("123456");
//        account.setUserId("user123");
//        // Mocking behavior of accountRepository
//        when(accountRepository.findByAccountNo(account.getAccountNo())).thenReturn(null);
//        ApiResponse response = accountService.createAccount(account);
//
//        ApiResponse expected= new ApiResponse("Account created Successfully",true);
//        // Assert
//        verify(accountRepository, times(1)).findByAccountNo(account.getAccountNo());
//        verify(accountRepository, times(1)).save(account);
//        assertEquals(expected, response);
//    }
//
//
//    // @Test
//    // void testCreateAccountWithExistingAccount() throws Exception {
//    //     Account account = new Account();
//    //     account.setAccountNo("123456");
//
//    //    // Mock the behavior of accountRepository.findByAccountNo() to return an existing account
//    //     when(accountRepository.findByAccountNo(account.getAccountNo())).thenReturn(new Account());
//
//    //     // Call the method to be tested and expect an AlreadyExistException
//    //  assertThrows(AlreadyExistException.class, () -> accountService.createAccount(account));
//    // }
//
//
//    //get account details for perticular userId
//    @Test
//    void testGetAccountByUserId() {
//        String userId = "user123";
//        List<Account> expectedAccounts = new ArrayList<>();
//
//        // Mock the behavior of accountRepository.getByUserId()
//        when(accountRepository.getByUserId(userId)).thenReturn(expectedAccounts);
//        List<Account> actualAccounts = accountService.get(userId);
//
//        // Assert
//        assertEquals(expectedAccounts, actualAccounts);
//    }
//
//    @Test
//    void testGetAccountByInvalidUserId() {
//        String invalidUserId = "invalidUser";
//
//        // Mock the behavior of accountRepository.getByUserId() to return null
//        when(accountRepository.getByUserId(invalidUserId)).thenReturn(null);
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> accountService.get(invalidUserId));
//        assertEquals(" Account not found ", exception.getMessage());
//
//    }
//
//
//    @Test
//   void testCreateOrUpdateAccountWithExistingAccount() {
//        String userId = "user123";
//        Account existingAccount = new Account();
//        existingAccount.setAccountId("existingAccountId");
//
//        // Mock the behavior of accountRepository.getByUserId()
//        when(accountRepository.getByUserId(userId)).thenReturn(Collections.singletonList(existingAccount));
//        ApiResponse response = accountService.updateAccountDetails(userId, existingAccount);
//
//        // Assert
//        verify(accountRepository, times(1)).getByUserId(userId);
//        verify(accountRepository, times(1)).save(existingAccount);
//        assertEquals("Account details Updated Successfully", response.getMessage());
//        assertTrue(response.getStatus());
//    }
//
//    @Test
//    void testCreateOrUpdateAccountWithNewAccount() {
//        String userId = "user123";
//        Account newAccount = new Account();
//
//        // Mock the behavior of accountRepository.getByUserId() to return an empty list
//        when(accountRepository.getByUserId(userId)).thenReturn(Collections.emptyList());
//
//        ApiResponse response = accountService.updateAccountDetails(userId, newAccount);
//
//        // Assert
//        verify(accountRepository, times(1)).getByUserId(userId);
//        verify(accountRepository, times(1)).save(newAccount);
//        assertEquals("Account created Successfully", response.getMessage());
//        assertTrue(response.getStatus());
//    }
//
//
//    @Test
//     void testUpdateBalanceWithExistingAccount() {
//
//        String accountId = "account123";
//        Account existingAccount = new Account();
//        existingAccount.setAccountId(accountId);
//        UpdateRequest updateRequest = new UpdateRequest(accountId, 100.0);
//
//        // Mock the behavior of accountRepository.findByAccountId()
//        when(accountRepository.findByAccountId(accountId)).thenReturn(existingAccount);
//        ApiResponse response = accountService.updateBalance(updateRequest);
//
//        // Assert
//        verify(accountRepository, times(1)).findByAccountId(accountId);
//        verify(accountRepository, times(1)).save(existingAccount);
//
//        assertEquals("Account Balance Updated Successfully", response.getMessage());
//        assertTrue(response.getStatus());
//        assertEquals(100.0, existingAccount.getBalance(), 0.001);  // Verify the updated balance
//    }
//}