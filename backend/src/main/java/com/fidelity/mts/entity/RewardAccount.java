package com.fidelity.mts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

import java.time.Instant;

import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class RewardAccount {

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "points_balance", nullable = false)
    private Long pointsBalance = 0L;

    @Column(nullable = false)
	@UpdateTimestamp
    private Instant lastUpdated;

    @Version
    private Integer version;

     Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getPointsBalance() {
        return pointsBalance;
    }

    public void setPointsBalance(Long pointsBalance) {
        this.pointsBalance = pointsBalance;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
