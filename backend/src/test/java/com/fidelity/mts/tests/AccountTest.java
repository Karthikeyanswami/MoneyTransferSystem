package com.fidelity.mts.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.enums.AccountStatus;
import com.fidelity.mts.exceptions.InsufficientBalanceException;

class AccountTest {

	private Account account;
    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);
        account.setHolderName("Test User");
        account.setBalance(new BigDecimal("1000.00"));
        account.setStatus(AccountStatus.ACTIVE);
        account.setVersion(0);
    }
    @Test
    void testDebit_Success() {
        BigDecimal debitAmount = new BigDecimal("500.00");
        BigDecimal expectedBalance = new BigDecimal("500.00");
        account.debit(debitAmount);
        assertEquals(expectedBalance, account.getBalance());
    }
    
    @Test
    void testDebit_InsufficientBalance() {
        BigDecimal debitAmount = new BigDecimal("1500.00");
        assertThrows(InsufficientBalanceException.class, 
            () -> account.debit(debitAmount));
    }
   
    @Test
    void testCredit_Success() {
        BigDecimal creditAmount = new BigDecimal("500.00");
        BigDecimal expectedBalance = new BigDecimal("1500.00");
        account.credit(creditAmount);
        assertEquals(expectedBalance, account.getBalance());
    }
    

}
