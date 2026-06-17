package com.fidelity.mts.exceptions;

public class AccountNotActiveException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AccountNotActiveException(String message) {
        super(message);
    }
}