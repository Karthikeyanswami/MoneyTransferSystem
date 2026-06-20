package com.fidelity.mts.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.RewardAccount;
import com.fidelity.mts.services.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins="http://localhost:4200")
public class AccountController {
	
	@Autowired AccountService service;
	
	@PostMapping
	public String createAccount(Account a) {
		long id = service.createAccount(a);
		return "Account with id "+id+" created";
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable int id) {
		Account a = service.getAccount(id);
		return ResponseEntity.status(HttpStatus.OK).body(a);
	}
	
	@GetMapping("/{id}/balance")
	public ResponseEntity<BigDecimal> getBalance(@PathVariable int id){
		BigDecimal balance = service.getBalance(id);
		return ResponseEntity.status(HttpStatus.OK).body(balance);
		
	}
	
	@GetMapping("/{id}/transactions")
	public ResponseEntity<?> getTransactions(@PathVariable int id){
		System.out.println( service.getTransactions(id));
		return service.getTransactions(id);
	}

	@GetMapping("/{id}/rewards")
    public Long getAccountRewards(@PathVariable int id) {
		System.out.println(service.getRewards(id));
        return service.getRewards(id);
    }

}
