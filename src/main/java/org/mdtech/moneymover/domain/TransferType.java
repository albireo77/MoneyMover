package org.mdtech.moneymover.domain;

public enum TransferType {
	
	CREDIT_TRANSFER_IN("ctin", "Credit Transfer Incoming"),
	CREDIT_TRANSFER_OUT("ctout", "Credit Transfer Outgoing"),
	DIRECT_DEBIT_IN("ddin", "Direct Debit Incoming"),
	DIRECT_DEBIT_OUT("ddout", "Direct Debit Outgoing"),
	UNKNOWN("unknown", "Unknown Transfer");
	
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
		return UNKNOWN;
	}
	
	@Override
	public String toString() {
		return description;
	}

}
