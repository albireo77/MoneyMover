package org.mdtech.moneymover.domain;

public enum TransferStatus {
	
	COMPLETED("Transfer Completed"),
	FAILED("Transfer Failed"),
	NEW("New Transfer"),
	IN_PROGRESS("Transfer in Progress...");
	
	private String description;
	
	TransferStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return description;
	}

}

