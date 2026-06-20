package com.fidelity.mts.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.RewardAccount;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.repo.AccountRepository;
import com.fidelity.mts.repo.RewardAccountRepository;
import com.fidelity.mts.repo.TransactionLogRepository;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired AccountRepository repo;
	@Autowired TransactionLogRepository trepo;
	@Autowired RewardAccountRepository rewardRepo;
	
	@Override
	public long createAccount(Account a) {
		repo.save(a);

		RewardAccount reward = new RewardAccount();
		reward.setAccountId(a.getId());
		reward.setPointsBalance(0L);
		

		rewardRepo.save(reward);
		return a.getId();
	}

	@Override
	public Account getAccount(int id) {
		Optional<Account> a = repo.findById(id);
		if (!a.isPresent())
			throw new AccountNotFoundException("Account not found");
		return a.get();
	}

	@Override
	public BigDecimal getBalance(int id) {
		Optional<Account> a = repo.findById(id);
		if (!a.isPresent())
			throw new AccountNotFoundException("Account not found");
		return a.get().getBalance();
	}

	@Override
	public ResponseEntity<?> getTransactions(int id) {
		Optional<List<TransactionLog>> fromresult = trepo.findByFromAccountId(id);
		Optional<List<TransactionLog>> toresult = trepo.findByToAccountId(id);
		if (!fromresult.isPresent() && !toresult.isPresent() ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions found");
		}
		List<TransactionLog> merged = Stream.concat(
		        fromresult.orElseGet(Collections::emptyList).stream(),
		        toresult.orElseGet(Collections::emptyList).stream()
		).toList();
		return ResponseEntity.status(HttpStatus.OK).body(merged);
	}

	@Override
	public Long getRewards(int id) {

		RewardAccount rewardAccount = rewardRepo.findById((long) id)
				.orElseThrow(() -> new AccountNotFoundException("Reward account not found"));

		return rewardAccount.getPointsBalance();
}

}
