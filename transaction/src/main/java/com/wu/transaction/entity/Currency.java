package com.wu.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private String code;
    private String name;
    private Double fee;
    private Double minFee;
    private Double maxFee;

}
