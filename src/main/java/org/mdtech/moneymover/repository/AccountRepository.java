package org.mdtech.moneymover.repository;

import org.mdtech.moneymover.entity.Account;
import org.mdtech.moneymover.error.RepositoryException;

public interface AccountRepository {
	
	void update(Account account) throws RepositoryException;
	
	Account getByNumber(String account) throws RepositoryException;

}
