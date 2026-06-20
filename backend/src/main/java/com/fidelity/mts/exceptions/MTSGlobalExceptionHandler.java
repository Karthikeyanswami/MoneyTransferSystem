package com.fidelity.mts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fidelity.mts.dto.ErrorResponse;

@ControllerAdvice
public class MTSGlobalExceptionHandler {
	
	@ExceptionHandler(value=AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> accountNotFound(AccountNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("ACC-404");
		errorResponse.setMessage(ex.getMessage());
	
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value=AccountNotActiveException.class)
	 
	public ResponseEntity<ErrorResponse> accountNotActive(AccountNotActiveException ex){
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("ACC-403");
		errorResponse.setMessage(ex.getMessage());
 
		return new ResponseEntity<>(errorResponse,HttpStatus.UNPROCESSABLE_ENTITY);
 
	}
 
	@ExceptionHandler(value=DuplicateTransferException.class)
 
	public ResponseEntity<ErrorResponse> duplicateTransfer(DuplicateTransferException ex){
 
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("TRX-409");
		errorResponse.setMessage(ex.getMessage());
 
		return new ResponseEntity<>(errorResponse,HttpStatus.UNPROCESSABLE_ENTITY);
 
	}
 
	@ExceptionHandler(value=InsufficientBalanceException.class)
 
	public ResponseEntity<ErrorResponse> insufficientBalance(InsufficientBalanceException ex){
 
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("TRX-400");
		errorResponse.setMessage(ex.getMessage());
 
		return new ResponseEntity<>(errorResponse,HttpStatus.UNPROCESSABLE_ENTITY);
 
	} 
}
