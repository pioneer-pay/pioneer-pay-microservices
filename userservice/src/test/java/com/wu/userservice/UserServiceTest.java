package com.wu.userservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wu.userservice.entity.User;
import com.wu.userservice.exception.AlreadyExistsException;
import com.wu.userservice.exception.InvalidCredentialsException;
import com.wu.userservice.exception.ResourceNotFoundException;
import com.wu.userservice.payload.ApiResponse;
import com.wu.userservice.repository.UserRepository;
import com.wu.userservice.service.UserRegiService;
import com.wu.userservice.service.impl.UserServiceImpl;
import com.wu.userservice.service.notification.NotificationServiceImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationServiceImpl notificationService;

    @InjectMocks
    private UserRegiService userService=new UserServiceImpl();

    private User existingUser;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        // Mocking behavior for userRepository
        when(userRepository.findByEmailId(anyString())).thenReturn(null);
        // Mocking behavior for passwordEncoder
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    }

    @Test
    public void testSaveUser() {
        // Create a sample user
        User user = new User();
        user.setEmailId("test@example.com");
        user.setPassword("password");

        // Call the method
        ApiResponse response = userService.saveUser(user);

        // Verify that userRepository.save() was called once
        verify(userRepository, times(1)).save(user);

        // Verify the response
        assertEquals("User registered successfully", response.getMessage());
        assertEquals(true, response.getStatus());
    }

    @Test
    public void testSaveUser_UserAlreadyExists() {
        // Create a sample user
        User user = new User();
        user.setEmailId("existing@example.com");
        user.setPassword("password");

        // Mock behavior for userRepository.findByEmailId()
        when(userRepository.findByEmailId("existing@example.com")).thenReturn(user);

        // Call the method and assert that it throws AlreadyExistsException
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            userService.saveUser(user);
        });

        // Verify that userRepository.save() was not called
        verify(userRepository, never()).save(user);
        // Assert the error message
        assertEquals("user with existing@example.com is already exist", exception.getMessage());
    }


    // @BeforeEach
    // public void beforeEach() {
    //     // Mocking behavior for userRepository.findByEmailId()
    //     when(userRepository.findByEmailId("existing@example.com")).thenReturn(new User("userId123",null, null, "existing@example.com", null, null, null, null, null, null, null, null, "encodedPassword"));
    //     when(userRepository.findByEmailId("nonexisting@example.com")).thenReturn(null);
        
    //     // Mocking behavior for passwordEncoder.matches()
    //     when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
    //     when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);
    // }

    // @Test
    // public void testLogin_Successful() {
    //     // Create a sample user
    //     User user = new User("userId123", null, null, "existing@example.com", null, null, null, null, null, null, null, null, "password");

    //     // Call the method
    //     ApiResponse response = userService.login(user);

    //     // Verify the response
    //     assertTrue(response.getStatus());
    //     assertEquals("User logged in successfully.", response.getMessage());
    //     assertEquals("userId123", response.getUserId());
    // }

    // @Test
    // public void testLogin_InvalidCredentials() {
    //     // Create a sample user with wrong password
    //     User user = new User("userId123", null, null, "existing@example.com", null, null, null, null, null, null, null, null, "wrongpassword");

    //     // Call the method and assert that it throws InvalidCredentialsException
    //     assertThrows(InvalidCredentialsException.class, () -> {
    //         userService.login(user);
    //     });
    // }

    // @Test
    // public void testLogin_UserNotFound() {
    //     // Create a sample user with non-existing email
    //     User user = new User(null, null, null, "nonexisting@example.com", null, null, null, null, null, null, null, null, "password");

    //     // Call the method and assert that it throws InvalidCredentialsException
    //     assertThrows(InvalidCredentialsException.class, () -> {
    //         userService.login(user);
    //     });
    // }


    @BeforeEach
    public void setUp() {
        existingUser = new User();
        existingUser.setUserId("123");
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setMobileNumber("1234567890");
        // existingUser.setDob("1990-01-01");
        existingUser.setAddress1("Address 1");
        existingUser.setCity("City");
        existingUser.setState("State");
        existingUser.setCountry("Country");
        existingUser.setZip("12345");
    }

    @Test
    public void testUpdateUser_Success() {
        String userId = "123";
        User updatedUser = new User();
        updatedUser.setFirstName("Updated John");
        updatedUser.setLastName("Updated Doe");
        updatedUser.setMobileNumber("0987654321");

        when(userRepository.findByUserId(userId)).thenReturn(existingUser);

        ApiResponse response = userService.updateUser(userId, updatedUser);

        assertNotNull(response);
        assertEquals("User detailed Updated Successfully!", response.getMessage());
        assertEquals(true, response.getStatus());
        assertEquals("123", response.getUserId());

        verify(userRepository, times(1)).save(existingUser);
        verify(notificationService, times(1)).createNotification(userId, "Your profile details have been updated.");
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        String userId = "456";
        User updatedUser = new User();

        when(userRepository.findByUserId(userId)).thenReturn(null);

        try {
            userService.updateUser(userId, updatedUser);
            fail("Expected ResourceNotFoundException was not thrown");
        } catch (ResourceNotFoundException e) {
           
        }

}

}
