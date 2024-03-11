package com.wu.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;
import feign.Retryer;

@Configuration
public class AccountFeignClientConfig {

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}

