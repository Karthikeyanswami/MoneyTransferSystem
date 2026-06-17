package com.fidelity.mts.dto;


import com.fidelity.mts.enums.TransactionStatus;
import java.math.BigDecimal;
import java.util.UUID;

public class TransferResponse {
    private UUID transactionId;
    private TransactionStatus status;
    private String message;
    private Long debitedFrom;
    private Long creditedTo;
    private BigDecimal amount;

    

    public UUID getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}



	public TransactionStatus getStatus() {
		return status;
	}



	public void setStatus(TransactionStatus status) {
		this.status = status;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public Long getDebitedFrom() {
		return debitedFrom;
	}



	public void setDebitedFrom(Long debitedFrom) {
		this.debitedFrom = debitedFrom;
	}



	public Long getCreditedTo() {
		return creditedTo;
	}



	public void setCreditedTo(Long creditedTo) {
		this.creditedTo = creditedTo;
	}



	public BigDecimal getAmount() {
		return amount;
	}



	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}



	@Override
    public String toString() {
        return "TransferResponse{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", debitedFrom=" + debitedFrom +
                ", creditedTo=" + creditedTo +
                ", amount=" + amount +
                '}';
    }
}