//package com.wu.userservice;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import com.wu.userservice.entity.User;
//import com.wu.userservice.exception.InvalidCredentialsException;
//import com.wu.userservice.payload.ApiResponse;
//import com.wu.userservice.repository.UserRepository;
//import com.wu.userservice.service.UserRegiService;
//
//
//@SpringBootTest
//@TestPropertySource("classpath:application-test.properties")
//class UserServiceTest {
//    @Autowired
//    private UserRegiService userRegiService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void testUserRegistration() throws Exception {
//        User user = new User();
//        user.setEmailId("user@gmail.com");
//        user.setPassword("Password");
//        User existingUser = userRepository.findByEmailId(user.getEmailId());
//        if (existingUser != null) {
//            userRepository.delete(existingUser);
//        }
//
//        ApiResponse registeredUser = userRegiService.saveUser(user);
//
//        ApiResponse expected = new ApiResponse("User registered successfully",true,user.getUserId());
//        assertEquals(expected, registeredUser);
//    }
//
//
//    @Test
//    void testUserLogin() throws Exception {
//        // Create a user for login
//        User user = new User();
//        user.setUserId("user@1234");
//        user.setEmailId("user1@gmail.com");
//        user.setPassword("Password");
//
//        User existingUser = userRepository.findByEmailId(user.getEmailId());
//        if (existingUser != null) {
//            userRepository.delete(existingUser);
//        }
//        userRepository.save(user);
//
//        // LoginResponse result = userRegiService.login(user);
//        assertThrows(InvalidCredentialsException.class, () -> {
//            userRegiService.login(user);
//        });
//
//    }
//
//    @Test
//    void testUpdateUser() {
//        User user = new User();
//        user.setUserId("user@3143");
//        user.setEmailId("user@gmail.com");
//        user.setPassword("password");
//
//        User existingUser = userRepository.findByEmailId(user.getEmailId());
//        if (existingUser != null) {
//            userRepository.delete(existingUser);
//        }
//        userRepository.save(user);
//        String id=user.getUserId();
//
//        String updatedFirstName="user1";
//        user.setFirstName(updatedFirstName);
//        String updatedLastName="abc";
//        user.setLastName(updatedLastName);
//
//        userRepository.save(user);
//        userRegiService.updateUser(id,user);
//
//        User updatedUser = userRepository.findById(user.getUserId()).orElse(null);
//
//        assertNotNull(updatedUser);
//        assertEquals(updatedFirstName, updatedUser.getFirstName());
//        assertEquals(updatedLastName, updatedUser.getLastName());
//    }
//}
