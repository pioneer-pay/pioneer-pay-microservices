package com.wu.userservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wu.userservice.entity.Account;
import com.wu.userservice.entity.Transaction;
import com.wu.userservice.entity.User;
import com.wu.userservice.payload.ApiResponse;
import com.wu.userservice.service.UserRegiService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class AuthController {
    
    @Autowired
    private UserRegiService userRegiService;

    

    //register user
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse>registerUser(@Valid @RequestBody User user) {
        ApiResponse apiResponse=userRegiService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    
    //login user
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse>loginUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userRegiService.login(user));
        
    }

    //update user details
   @PutMapping("/update/{userId}")
   public ResponseEntity<ApiResponse>updateUser(@PathVariable String userId, @RequestBody User user){
    ApiResponse apiResponse=userRegiService.updateUser(userId, user);
    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
   }

   
   //get user details
   @GetMapping("/{userId}")
   public ResponseEntity<User>getUser(@PathVariable String userId){
    return ResponseEntity.status(HttpStatus.OK).body(userRegiService.getUserDetails(userId));
   }

   //get all users
   @GetMapping("/get")
   public ResponseEntity<List<User>>getAllUsers(){
    return ResponseEntity.status(HttpStatus.OK).body(userRegiService.getAll());
   }

   //show account details
   @GetMapping("/account/{userId}")
    public ResponseEntity<List<Account>> getUserDetails(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userRegiService.getAccountDetails(userId));
    }

    //transaction histroy
    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<Transaction>> showTransactions(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userRegiService.showTransactions(userId));
    }


    //generate otp
     @GetMapping("/otp")
     public ResponseEntity<String> generateOtp() {
         String otp = userRegiService.generateOtp();
         return ResponseEntity.status(HttpStatus.OK).body(otp);
     }


    @GetMapping("/otp/send/{email}")
    public ResponseEntity<String> generateOtpAndSend(@PathVariable String email) {
        try {
            String otp = userRegiService.generateOtpAndSend(email);
            return ResponseEntity.status(HttpStatus.OK).body("OTP sent successfully to " + email);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP to " + email);
        }
    }

    @PostMapping("/verification/{email}")
    public ResponseEntity<ApiResponse> verifyEmail(@PathVariable String email, @RequestParam String otp) {
        ApiResponse response = userRegiService.verifyEmail(email, otp);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //get emailId of user
    @GetMapping("/get/email/{userId}")
    public ResponseEntity<String>getEmailByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userRegiService.getEmailByUserId(userId));
    }    

}
