package com.fidelity.mts.dto;

import com.fidelity.mts.entity.Account;
import java.math.BigDecimal;

public class AccountResponse {
    private Account account;

   
    public String getDetails(){
        return "Account ID : "+ account.getId() + "\nHolder Name: "+ account.getHolderName()
                + "\nStatus : "+account.getStatus();   }

    public BigDecimal getBalance(){
        return account.getBalance();   }
}


