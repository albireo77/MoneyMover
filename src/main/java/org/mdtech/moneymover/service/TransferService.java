package org.mdtech.moneymover.service;

import org.mdtech.moneymover.error.Error;
import org.mdtech.moneymover.domain.Transfer;

import java.util.List;

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
     * @param {@link Transfer} object
     * @return list of validation errors
	 */
	List<Error> validate(Transfer transfer);
}
