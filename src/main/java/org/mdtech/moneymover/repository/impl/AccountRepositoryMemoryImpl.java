package org.mdtech.moneymover.repository.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mdtech.moneymover.entity.Account;
import org.mdtech.moneymover.error.RepositoryException;
import org.mdtech.moneymover.repository.AccountRepository;

public class AccountRepositoryMemoryImpl implements AccountRepository {
	
	private final Map<String, Account> repo;
	
	public AccountRepositoryMemoryImpl() {
		
		repo = new HashMap<>(5);
		Account account = Account.of("PL10105000997603123456789123", BigDecimal.ZERO);
		repo.put(account.getIban(), account);
		account = Account.of("TR320010009999901234567890", new BigDecimal("0.01"));
		repo.put(account.getIban(), account);
		account = Account.of("QA54QNBA000000000000693123456", new BigDecimal("27.5"));
		repo.put(account.getIban(), account);
		account = Account.of("LU120010001234567891", new BigDecimal("100"));
		repo.put(account.getIban(), account);
		account = Account.of("NO8330001234567", BigDecimal.TEN);
		repo.put(account.getIban(), account);
	}
	
	public AccountRepositoryMemoryImpl(List<Account> accountList) {
		
		repo = new HashMap<String, Account>(accountList.size());
		for (Account account : accountList) {
			repo.put(account.getIban(), account);
		}
		
	}

	@Override
	public void update(Account account) throws RepositoryException {
		repo.put(account.getIban(), account);		
	}

	@Override
	public Account getByIban(String iban) throws RepositoryException {
		return repo.get(iban);
	}

}
