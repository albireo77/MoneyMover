package org.mdtech.moneymover.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mdtech.moneymover.error.Error;

public class Transfer {
	
	private static final Logger LOG = Logger.getLogger(Transfer.class);
	
	private TransferType type;
	private BigDecimal amount;
	private String origAccount;
	private String destAccount;
	private TransferStatus status = TransferStatus.NEW;
	private List<Error> errors;

	public static Transfer of(String code, String origAccount, String destAccount, String amount) {
		
		Transfer transfer = new Transfer();
		try {
			transfer.setAmount(new BigDecimal(amount));
		} catch (NumberFormatException e) {
			LOG.error(String.format("Incorrect amount (%s) in transaction between account %s and %s", amount, origAccount, destAccount));
		}		
		transfer.setType(TransferType.findByCode(code));
		transfer.setOrigAccount(origAccount);
		transfer.setDestAccount(destAccount);
		return transfer;		
	}
	
	public TransferType getType() {
		return type;
	}
	
	public void setType(TransferType type) {
		this.type = type;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getOrigAccount() {
		return origAccount;
	}
	
	public void setOrigAccount(String origAccount) {
		this.origAccount = origAccount;
	}
	
	public String getDestAccount() {
		return destAccount;
	}
	
	public void setDestAccount(String destAccount) {
		this.destAccount = destAccount;
	}

	public TransferStatus getStatus() {
		return status;
	}

	public void setStatus(TransferStatus status) {
		this.status = status;
	}

	public List<Error> getErrors() {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		return errors;
	}
	
}
