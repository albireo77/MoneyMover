package org.mdtech.moneymover.utils;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.mdtech.moneymover.repository.AccountRepository;
import org.mdtech.moneymover.repository.impl.AccountRepositoryMemoryImpl;
import org.mdtech.moneymover.service.BalanceService;
import org.mdtech.moneymover.service.TransferService;
import org.mdtech.moneymover.service.impl.BalanceServiceImpl;
import org.mdtech.moneymover.service.impl.TransferServiceImpl;

import javax.inject.Singleton;

public class ApplicationBinder extends AbstractBinder {
	
	@Override
	protected void configure() {
		bind(TransferServiceImpl.class).to(TransferService.class).in(Singleton.class);
		bind(BalanceServiceImpl.class).to(BalanceService.class).in(Singleton.class);
		bind(AccountRepositoryMemoryImpl.class).to(AccountRepository.class).in(Singleton.class);
	}
}
