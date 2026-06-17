package com.fidelity.mts.services;


import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fidelity.mts.dto.ErrorResponse;
import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.dto.TransferResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.enums.AccountStatus;
import com.fidelity.mts.enums.TransactionStatus;
import com.fidelity.mts.exceptions.AccountNotActiveException;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.exceptions.DuplicateTransferException;
import com.fidelity.mts.exceptions.InsufficientBalanceException;
import com.fidelity.mts.repo.AccountRepository;
import com.fidelity.mts.repo.TransactionLogRepository;

@Service
public class TransferServiceImpl implements TransferService{

	TransferRequest transferRequest;
	String failureReason;
	String errorCode;
	@Autowired TransactionLogRepository trepo;
	@Autowired AccountRepository repo;
	
	@Override
	public ResponseEntity<?> transfer(TransferRequest tr) {
		this.transferRequest = tr;
		
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setFromAccountId(tr.getFromAccountId());
		transactionLog.setToAccountId(tr.getToAccountId());
		transactionLog.setIdempotencyKey(tr.getIdempotencyKey());
		transactionLog.setAmount(tr.getAmount());
		
		
		if (!validateTransfer(transactionLog)) {
			
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorCode(this.errorCode);
			errorResponse.setMessage(this.failureReason);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
			
			
		}
		else {

			transactionLog.setStatus(TransactionStatus.SUCCESS);
			trepo.save(transactionLog);
			
			if (executeTransfer());
			{
				TransferResponse transferResponse = new TransferResponse();
				transferResponse.setTransactionId(transactionLog.getId());
				transferResponse.setAmount(transactionLog.getAmount());
				transferResponse.setDebitedFrom(transactionLog.getFromAccountId());
				transferResponse.setCreditedTo(transactionLog.getToAccountId());
				transferResponse.setStatus(TransactionStatus.SUCCESS);
				transferResponse.setMessage("Transfer completed");
		
				return ResponseEntity.status(HttpStatus.OK).body(transferResponse);
			}
			
			
		}
		
		
	}
	@Override
	public boolean validateTransfer(TransactionLog transactionLog) {

		 if (transferRequest.getFromAccountId() == transferRequest.getToAccountId()) {
		            this.failureReason = "Accounts must be different";
		            this.errorCode = "VAL-422";
		            transactionLog.setStatus(TransactionStatus.FAILED);
					 transactionLog.setFailureReason(this.failureReason);
					 trepo.save(transactionLog);
		            return false;
			 
		        }
		 Optional<Account> f = repo.findById(transferRequest.getFromAccountId());
		 Optional<Account> t = repo.findById(transferRequest.getToAccountId());
		 if (!f.isPresent())
		 {	
	         throw new AccountNotFoundException( "Source account must exist");
		 }
		 if (!t.isPresent()) {
			 throw new AccountNotFoundException( "Destination account must exist");
	         
		 }
		 if(f.get().getStatus() != AccountStatus.ACTIVE) {
			 transactionLog.setStatus(TransactionStatus.FAILED);
			 transactionLog.setFailureReason("Source account must be ACTIVE");
			 trepo.save(transactionLog);
			 
			 throw new AccountNotActiveException("Source account must be ACTIVE");
		 }
		 if(t.get().getStatus() != AccountStatus.ACTIVE) {
			 transactionLog.setStatus(TransactionStatus.FAILED);
			 transactionLog.setFailureReason("Destination account must be ACTIVE");
			 trepo.save(transactionLog);
			
			 throw new AccountNotActiveException("Destination account must be ACTIVE");
		 }
		 if (transferRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			 this.errorCode = "VAL-422";
			 this.failureReason = "Amount must be > 0";
			 transactionLog.setStatus(TransactionStatus.FAILED);
			 transactionLog.setFailureReason(this.failureReason);
			 trepo.save(transactionLog);
			 
			 return false;
		 }
		 
		 if (f.get().getBalance().compareTo(transferRequest.getAmount()) <= 0) {
			 transactionLog.setStatus(TransactionStatus.FAILED);
			 transactionLog.setFailureReason("Insufficient Balance");
			 trepo.save(transactionLog);
			 throw new InsufficientBalanceException("Insufficient Balance");
		 }
		 var keyExsists = trepo.findByIdempotencyKey(transferRequest.getIdempotencyKey());
		 if(keyExsists.isPresent()) {
			 throw new DuplicateTransferException("Idempotency key must be unique");
		 }
		 
		return true;
	}
	@Override
	public boolean executeTransfer() {
		BigDecimal amount = transferRequest.getAmount();
		 Optional<Account> f = repo.findById(transferRequest.getFromAccountId());
		 Optional<Account> t = repo.findById(transferRequest.getToAccountId());
		 if (f.isPresent() && t.isPresent()) {
			f.get().debit(amount);
			t.get().credit(amount);
			
			repo.save(f.get());
			repo.save(t.get());
			return true;
		 }
		
		 return false;

	}
	
}
