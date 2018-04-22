package org.mdtech.moneymover.service.impl;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.mdtech.moneymover.domain.Transfer;
import org.mdtech.moneymover.domain.TransferStatus;
import org.mdtech.moneymover.domain.TransferType;
import org.mdtech.moneymover.entity.Account;
import org.mdtech.moneymover.error.ServiceException;
import org.mdtech.moneymover.repository.AccountRepository;
import org.mdtech.moneymover.service.TransferService;

public class TransferServiceImpl implements TransferService {	
	
	private static final Logger LOG = Logger.getLogger(TransferServiceImpl.class);
	private final AccountRepository accountRepository;
	
	@Inject
	public TransferServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void processCreditTransfer(Transfer transfer) {
		
		LOG.info("Processing credit transfer for account " + transfer.getIBAN());
		
		Account account = accountRepository.getByIban(transfer.getIBAN());
		BigDecimal newAmount = account.getAmount().add(transfer.getAmount());
		account.setAmount(newAmount);
		accountRepository.update(account);
	}

	@Override
	public void processDebitTransfer(Transfer transfer) {
		
		LOG.info("Processing debit transfer for account " + transfer.getIBAN());
		
		Account account = accountRepository.getByIban(transfer.getIBAN());
		BigDecimal newAmount = account.getAmount().subtract(transfer.getAmount());
		if (newAmount.signum() < 0) {
			transfer.setStatus(TransferStatus.FAILED);
			transfer.setErrorInfo("Insufficient funds on account");
			throw new ServiceException(transfer);
		}
		account.setAmount(newAmount);
		accountRepository.update(account);
	}

	@Override
	public boolean validate(Transfer transfer) {

		if (transfer.getType() == TransferType.UNKNOWN) {
			transfer.setErrorInfo("Unknown transfer type");
		} else if (transfer.getAmount() == null) {
			transfer.setErrorInfo("Incorrect amount");
		} else if (transfer.getAmount().signum() < 0) {
			transfer.setErrorInfo("Negative amount");
		} else {
			Account account = accountRepository.getByIban(transfer.getIBAN());
			if (account == null) {
				transfer.setErrorInfo("Account not found in repository");
			}
		}
		return transfer.getErrorInfo() == null;
	}


}
