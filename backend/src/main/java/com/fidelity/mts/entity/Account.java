package com.fidelity.mts.entity;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.UpdateTimestamp;

import com.fidelity.mts.enums.AccountStatus;
import com.fidelity.mts.exceptions.InsufficientBalanceException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;


@Entity
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", columnDefinition = "BIGINT")
    private long id;
	
	@Column(nullable=false, length=255)
    private String holderName;
	
	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal balance;

	@Column(nullable=false, length=20)
    private AccountStatus status;
	
	//@Column(nullable = false)
	@Version
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int version;
	
	@Column(nullable = false)
	@UpdateTimestamp
    private Instant lastUpdated;

    // Getters/Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }


    public void debit(BigDecimal amount) {
    	if (this.balance.compareTo(amount) <= 0)
    		throw new InsufficientBalanceException("Insufficient balance");
    	this.balance = this.balance.subtract(amount);
    	
    }

    public void credit(BigDecimal amount) {
    	this.balance = this.balance.add(amount);
    	System.out.println("credit"+this.balance);
    }
    public AccountStatus isActive() {
       return this.status;
    }

   
    

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                ", status=" + status +
                ", version=" + version +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}

