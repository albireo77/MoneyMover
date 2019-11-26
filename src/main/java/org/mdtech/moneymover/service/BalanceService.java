package org.mdtech.moneymover.service;

import org.mdtech.moneymover.domain.BalanceRequest;

public interface BalanceService {
	
	/**
	 * Process credit transfer. Account will be credited. 
     * @param Transfer object
	 */
	void processRequest(BalanceRequest request);
}
