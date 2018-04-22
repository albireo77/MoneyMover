package org.mdtech.moneymover.domain;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class Transfer {
	
	private static final Logger LOG = Logger.getLogger(Transfer.class);
	
	private TransferType type;
	private BigDecimal amount;
	private String iban;
	private TransferStatus status = TransferStatus.NEW;
	private String errorInfo;
	
	public static Transfer of(String code, String iban, String amount) {
		
		Transfer transfer = new Transfer();
		try {
			transfer.setAmount(new BigDecimal(amount));
		} catch (NumberFormatException e) {
			LOG.error(String.format("Incorrect amount (%s) in transaction for account %s", amount, iban));
		}		
		transfer.setType(TransferType.findByCode(code));
		transfer.setIBAN(iban);
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
	
	public String getIBAN() {
		return iban;
	}
	
	public void setIBAN(String iban) {
		this.iban = iban;
	}

	public TransferStatus getStatus() {
		return status;
	}

	public void setStatus(TransferStatus status) {
		this.status = status;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
