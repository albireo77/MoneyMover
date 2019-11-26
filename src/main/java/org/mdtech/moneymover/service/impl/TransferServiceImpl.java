package org.mdtech.moneymover.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.mdtech.moneymover.domain.Transfer;
import org.mdtech.moneymover.entity.Account;
import org.mdtech.moneymover.repository.AccountRepository;
import org.mdtech.moneymover.service.TransferService;
import org.mdtech.moneymover.error.Error;

public class TransferServiceImpl implements TransferService {	
	
	private static final Logger LOG = Logger.getLogger(TransferServiceImpl.class);
	private final AccountRepository accountRepository;
	
	@Inject
	public TransferServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void processCreditTransfer(Transfer transfer) {
		
		LOG.info("Processing credit transfer for origin account " + transfer.getOrigAccount());
		
		Account origAccount = accountRepository.getByNumber(transfer.getOrigAccount());
		Account destAccount = accountRepository.getByNumber(transfer.getDestAccount());

		BigDecimal balance = origAccount.getBalance().subtract(transfer.getAmount());
		if (balance.signum() == -1) {
			transfer.getErrors().add(new Error("Insufficient funds on origin account"));
			return;
		}

		destAccount.setBalance(destAccount.getBalance().add(transfer.getAmount()));
		origAccount.setBalance(origAccount.getBalance().subtract(transfer.getAmount()));

		accountRepository.update(destAccount);
		accountRepository.update(origAccount);
	}

	@Override
	public void processDebitTransfer(Transfer transfer) {
		
		LOG.info("Processing debit transfer for origin account " + transfer.getOrigAccount());

		Account origAccount = accountRepository.getByNumber(transfer.getOrigAccount());
		Account destAccount = accountRepository.getByNumber(transfer.getDestAccount());

		BigDecimal balance = destAccount.getBalance().subtract(transfer.getAmount());
		if (balance.signum() == -1) {
			transfer.getErrors().add(new Error("Insufficient funds on destination account"));
			return;
		}

		destAccount.setBalance(destAccount.getBalance().subtract(transfer.getAmount()));
		origAccount.setBalance(origAccount.getBalance().add(transfer.getAmount()));

		accountRepository.update(destAccount);
		accountRepository.update(origAccount);
	}

	@Override
	public List<Error> validate(Transfer transfer) {

		List<Error> errors = new ArrayList<>();

		if (transfer.getType() == null) {
			Error error = new Error("Unknown transfer type");
			errors.add(error);
		}

		if (transfer.getAmount() == null) {
			Error error = new Error("No amount");
			errors.add(error);
		} else if (transfer.getAmount().signum() != 1) {
			Error error = new Error("Negative or zero amount of transfer");
			errors.add(error);
		}

		Account account = accountRepository.getByNumber(transfer.getOrigAccount());
		if (account == null) {
			Error error = new Error("Origin account not found in repository");
			errors.add(error);
		}

		account = accountRepository.getByNumber(transfer.getDestAccount());
		if (account == null) {
			Error error = new Error("Destination account not found in repository");
			errors.add(error);
		}

		return errors;
	}
}
