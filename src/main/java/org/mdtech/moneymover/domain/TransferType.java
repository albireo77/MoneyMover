package org.mdtech.moneymover.domain;

public enum TransferType {
	
	CREDIT_TRANSFER("ct", "Credit Transfer"),
	DIRECT_DEBIT("dd", "Direct Debit"),
	BALANCE("bl", "Balance");
	
	private String code;
	private String description;
	
	TransferType(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public String code() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static TransferType findByCode(String code) {
		
		for (TransferType type: values()) {
			if (type.code().equalsIgnoreCase(code)) {
				return type;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return description;
	}

}
