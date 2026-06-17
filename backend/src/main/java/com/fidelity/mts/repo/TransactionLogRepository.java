package com.fidelity.mts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelity.mts.entity.TransactionLog;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog,String> {
	Optional<TransactionLog> findByIdempotencyKey(String idempotencyKey);
	Optional<List<TransactionLog>> findByFromAccountId(int fromAccountId);
	Optional<List<TransactionLog>> findByToAccountId(int toAccountId);
}
