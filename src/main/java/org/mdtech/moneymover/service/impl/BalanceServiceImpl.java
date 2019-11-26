package org.mdtech.moneymover.service.impl;

import org.apache.log4j.Logger;
import org.mdtech.moneymover.domain.BalanceRequest;
import org.mdtech.moneymover.entity.Account;
import org.mdtech.moneymover.error.Error;
import org.mdtech.moneymover.repository.AccountRepository;
import org.mdtech.moneymover.service.BalanceService;

import javax.inject.Inject;

public class BalanceServiceImpl implements BalanceService {

	private static final Logger LOG = Logger.getLogger(BalanceServiceImpl.class);

	private final AccountRepository accountRepository;

	@Inject
	public BalanceServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void processRequest(BalanceRequest request) {

		Account account = accountRepository.getByNumber(request.getAccount());
		if (account == null) {
			Error error = new Error("Account not found in account repository");
			request.getErrors().add(error);
		} else {
			request.setBalance(account.getBalance());
		}
	}
}
