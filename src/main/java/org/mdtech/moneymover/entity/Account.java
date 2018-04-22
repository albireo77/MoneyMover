package org.mdtech.moneymover.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
	
	@Id
	@GeneratedValue
	long accountId;

	String iban;
	BigDecimal amount;
	
	public static Account of(String iban, BigDecimal amount) {
		
		Account account = new Account();
		account.setIban(iban);
		account.setAmount(amount);
		return account;
	}

	public long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		return true;
	}
	
}
