package com.fidelity.mts.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.fidelity.mts.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionLog {

	@Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM) // v4-style random UUID
    @JdbcTypeCode(SqlTypes.VARCHAR)                    // store as VARCHAR in DB
    @Column( length = 36, nullable = false, updatable = false)
    private UUID id;
	
	@Column(columnDefinition = "BIGINT", name="from_account_id",nullable = false)
    private long fromAccountId;
	
	@Column(columnDefinition = "BIGINT", name="to_account_id",nullable = false)
    private long toAccountId;
	
	@Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;
	
	@Column(nullable=false, length=20)
    private TransactionStatus status;
	
	@Column(nullable=true, length=255)
    private String failureReason;
	
	@Column( length = 100, nullable = false, unique = true)
    private String idempotencyKey;
	
	@CreationTimestamp
	@Column( nullable = false, updatable = false)
    private Instant createdOn;
	
	@ManyToOne
	@JoinColumn(
			name="from_account_id",
			referencedColumnName = "id",
			insertable = false,
			updatable = false
	)
	private Account FromAccount;
 
	@ManyToOne
	@JoinColumn(
			name="to_account_id",
			referencedColumnName = "id",
			insertable = false,
			updatable = false
	)
	private Account ToAccount;
	

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public long getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(long fromAccountId) { this.fromAccountId = fromAccountId; }
    public long getToAccountId() { return toAccountId; }
    public void setToAccountId(long toAccountId) { this.toAccountId = toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount;}
    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
    public Instant getCreatedOn() { return createdOn; }
    public void setCreatedOn(Instant createdOn) { this.createdOn = createdOn; }

    @Override
    public String toString() {

        return "TransactionLog{" +
                "id=" + id +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", amount=" + amount +
                ", status=" + status +
                ", failureReason='" + failureReason + '\'' +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }

}


