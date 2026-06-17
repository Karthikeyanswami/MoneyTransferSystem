package com.fidelity.mts.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;

public interface AccountService {
	long createAccount(Account a);
	Account getAccount(int id);
	BigDecimal getBalance(int id);
	ResponseEntity<?> getTransactions(int id);
	
	
	
}
