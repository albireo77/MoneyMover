package org.mdtech.moneymover.utils;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.mdtech.moneymover.repository.AccountRepository;
import org.mdtech.moneymover.repository.impl.AccountRepositoryMemoryImpl;
import org.mdtech.moneymover.service.TransferService;
import org.mdtech.moneymover.service.impl.TransferServiceImpl;

public class ApplicationBinder extends AbstractBinder {
	
	@Override
	protected void configure() {
		bind(TransferServiceImpl.class).to(TransferService.class);
		bind(AccountRepositoryMemoryImpl.class).to(AccountRepository.class);
	}
}
