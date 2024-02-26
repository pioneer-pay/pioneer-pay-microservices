package com.wu.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@ComponentScan(basePackages = {"com.wu.userservice.repository"})
//@EntityScan("com.wu.userservice.entity")
//@Configuration
//@EnableAutoConfiguration
@SpringBootApplication(exclude=SecurityAutoConfiguration.class)
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
