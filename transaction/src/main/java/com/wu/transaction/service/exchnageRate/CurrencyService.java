package com.wu.transaction.service.exchnageRate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wu.transaction.entity.Currency;

public class CurrencyService {
    private List<Currency> currencies;

    public CurrencyService() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/application.json");
        currencies = objectMapper.readValue(inputStream, new TypeReference<List<Currency>>(){});
    }

    public double getFeeByCode(String code) {
        for (Currency currency : currencies) {
            if (currency.getCode().equals(code)) {
                return currency.getFee();
            }
        }
        return 0.0;
    }
    public double getMinFeeByCode(String code) {
        for (Currency currency : currencies) {
            if (currency.getCode().equals(code)) {
                return currency.getMinFee();
            }
        }
        return 0.0;
    }
    public double getMaxFeeByCode(String code) {
        for (Currency currency : currencies) {
            if (currency.getCode().equals(code)) {
                return currency.getMaxFee();
            }
        }
        return 0.0;
    }
}
