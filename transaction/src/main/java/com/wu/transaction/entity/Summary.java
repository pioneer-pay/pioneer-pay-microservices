package com.wu.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Summary {
    private Double amount;
    private Double commission;
    private Double receivedMoney;
    private Double rate;
    private String baseCurrencyCode;
    private String targetCurrencyCode;

}
