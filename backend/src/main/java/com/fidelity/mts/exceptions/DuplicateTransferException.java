package com.fidelity.mts.exceptions;

public class DuplicateTransferException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateTransferException(String message) {
        super(message);
    }
}