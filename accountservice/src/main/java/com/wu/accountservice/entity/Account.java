package com.wu.accountservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="account")
public class Account {
    @Id
    private String accountId;
    private String accountHolderName;
    @Column(unique = true)
    @Size(min=8,max=17)
    private String accountNo;
    private String userId;
    @Column(length = 11 , unique = true)
    private String ifscCode;
    private String bankName;
    private Double balance;

    

}
