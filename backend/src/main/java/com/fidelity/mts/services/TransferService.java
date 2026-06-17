package com.fidelity.mts.services;



import org.springframework.http.ResponseEntity;

import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.entity.TransactionLog;


public interface TransferService {
	ResponseEntity<?> transfer(TransferRequest tr);
	boolean validateTransfer(TransactionLog transactionLog);
	boolean executeTransfer();
	
}
