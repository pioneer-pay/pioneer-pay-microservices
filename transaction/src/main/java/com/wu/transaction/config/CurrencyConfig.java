package com.wu.transaction.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wu.transaction.service.exchnageRate.CurrencyService;

@Configuration
public class CurrencyConfig {
    @Bean
    public CurrencyService currencyService() throws IOException {
        return new CurrencyService();
    }
}
