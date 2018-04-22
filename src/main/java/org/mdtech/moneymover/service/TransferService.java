package org.mdtech.moneymover.service;

import org.mdtech.moneymover.domain.Transfer;

public interface TransferService {
	
	/**
	 * Process credit transfer. Account will be credited. 
     * @param Transfer object
	 */
	void processCreditTransfer(Transfer transfer);

	/**
	 * Process debit transfer. Account will be debited. 
     * @param Transfer object
	 */
	void processDebitTransfer(Transfer transfer);

	/**
	 * Validates transfer data. 
     * @param Transfer object
     * @return true if transfer is valid
	 */
	boolean validate(Transfer transfer);
}
