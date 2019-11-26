package org.mdtech.moneymover.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mdtech.moneymover.domain.Transfer;
import org.mdtech.moneymover.domain.TransferType;
import org.mdtech.moneymover.entity.Account;
import org.mdtech.moneymover.error.ServiceException;
import org.mdtech.moneymover.repository.AccountRepository;
import org.mdtech.moneymover.service.impl.TransferServiceImpl;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

public class TransferServiceImplTest {
	
	TransferService service;
	AccountRepository repository;
	
	@Before
	public void setUp() {
		repository = mock(AccountRepository.class);
		service = new TransferServiceImpl(repository);
	}
	
	@Test
	public void testCreditAccountForCreditTransferIn() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban10", "iban20", "10");
		Account origAccount = Account.of(transfer.getOrigAccount(), new BigDecimal("25"));
		Account destAccount = Account.of(transfer.getDestAccount(), new BigDecimal("77"));
		when(repository.getByNumber(transfer.getOrigAccount())).thenReturn(origAccount);
		when(repository.getByNumber(transfer.getDestAccount())).thenReturn(destAccount);
				
		// when
		service.processCreditTransfer(transfer);
		
		//then
		assertThat(origAccount.getBalance().intValue()).isEqualTo(15);
		assertThat(destAccount.getBalance().intValue()).isEqualTo(87);
	}
	
	@Test
	public void testCreditAccountForDirectDebitOut() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.DIRECT_DEBIT.code(), "iban1", "iban2","15");
		Account origAccount = Account.of(transfer.getOrigAccount(), new BigDecimal("20"));
		Account destAccount = Account.of(transfer.getDestAccount(), new BigDecimal("50"));
		when(repository.getByNumber(transfer.getOrigAccount())).thenReturn(origAccount);
		when(repository.getByNumber(transfer.getDestAccount())).thenReturn(destAccount);
				
		// when
		service.processDebitTransfer(transfer);
		
		//then
		assertThat(origAccount.getBalance().intValue()).isEqualTo(35);
		assertThat(destAccount.getBalance().intValue()).isEqualTo(35);
	}
	
	/*@Test
	public void testDebitAccountForCreditTransferOut() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban1", "1");
		Account account = Account.of(transfer.getIBAN(), BigDecimal.ONE);
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		service.processDebitTransfer(transfer);
		
		//then
		assertThat(account.getAmount().intValue()).isZero();
	}
	
	@Test
	public void testDebitAccountForDirectDebitIn() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.DIRECT_DEBIT.code(), "iban3", "3");
		Account account = Account.of(transfer.getIBAN(), BigDecimal.TEN);
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		service.processDebitTransfer(transfer);
		
		//then
		assertThat(account.getAmount().intValue()).isEqualTo(7);
	}
	
	@Test
	public void testDebitAccountForEqualsZero() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban10.59", "10.59");
		Account account = Account.of(transfer.getIBAN(), new BigDecimal("10.59"));
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		service.processDebitTransfer(transfer);
		
		//then
		assertThat(account.getAmount().compareTo(BigDecimal.ZERO)).isZero();
	}
	
	@Test(expected = ServiceException.class)
	public void testDebitAccountForInsufficientFunds() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban48.5", "48.5");
		Account account = Account.of(transfer.getIBAN(), BigDecimal.TEN);
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		service.processDebitTransfer(transfer);
	}
	
	@Test
	public void testValidateIncorrectAmount() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban", "blablabla");
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		assertThat(transfer.getErrorInfo()).isEqualTo("Incorrect amount");
		assertThat(result).isFalse();
	}
	
	@Test
	public void testValidateNegativeAmount() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban", "-4");
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		assertThat(transfer.getErrorInfo()).isEqualTo("Negative amount");
		assertThat(result).isFalse();
	}
	
	@Test
	public void testValidateUnknownTransferType() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.UNKNOWN.code(), "iban", "4");
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		assertThat(transfer.getErrorInfo()).isEqualTo("Unknown transfer type");
		assertThat(result).isFalse();
	}
	
	@Test
	public void testValidateNotExistingAccount() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.DIRECT_DEBIT.code(), "blablabla", "3");
		when(repository.getByIban(transfer.getIBAN())).thenReturn(null);
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		verify(repository, times(1)).getByIban(any(String.class));
		assertThat(transfer.getErrorInfo()).isEqualTo("Account not found in repository");
		assertThat(result).isFalse();
	}
	
	@Test
	public void testValidatePositiveCreditTransferIn() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban4", "4");
		Account account = Account.of(transfer.getIBAN(), BigDecimal.TEN);
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		assertThat(transfer.getErrorInfo()).isNull();
		assertThat(result).isTrue();
	}
	
	@Test
	public void testValidatePositiveCreditTransferOut() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.CREDIT_TRANSFER.code(), "iban1", "1");
		Account account = Account.of(transfer.getIBAN(), BigDecimal.ONE);
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		assertThat(transfer.getErrorInfo()).isNull();
		assertThat(result).isTrue();
	}
	
	@Test
	public void testValidatePositiveDirectDebitIn() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.DIRECT_DEBIT.code(), "iban4", "4");
		Account account = Account.of(transfer.getIBAN(), BigDecimal.TEN);
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		assertThat(transfer.getErrorInfo()).isNull();
		assertThat(result).isTrue();
	}
	
	@Test
	public void testValidatePositiveDirectDebitOut() {
		
		// given
		Transfer transfer = Transfer.of(TransferType.DIRECT_DEBIT.code(), "iban1", "1");
		Account account = Account.of(transfer.getIBAN(), BigDecimal.ONE);
		when(repository.getByIban(transfer.getIBAN())).thenReturn(account);
				
		// when
		boolean result = service.validate(transfer);
		
		//then
		assertThat(transfer.getErrorInfo()).isNull();
		assertThat(result).isTrue();
	}*/
}