package org.mdtech.moneymover.controller;
 
import org.apache.log4j.Logger;

import org.mdtech.moneymover.domain.Transfer;
import org.mdtech.moneymover.domain.TransferStatus;
import org.mdtech.moneymover.error.ServiceException;
import org.mdtech.moneymover.service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/transfer")
public class TransferController {
	
	private final TransferService service;
	
	@Inject
	public TransferController(TransferService service) {
		this.service = service;
	}
	
	private static final Logger LOG = Logger.getLogger(TransferController.class);
 
	@Path("/{code}/{iban}/{amount}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response processTransfer(@PathParam("code")String code, @PathParam("iban")String iban, @PathParam("amount")String amount) {
		
		LOG.info("Start processing payment transfer for account " + iban);
		
		Transfer transfer = Transfer.of(code, iban, amount);
		
		if (!service.validate(transfer)) {
			transfer.setStatus(TransferStatus.FAILED);
			LOG.error(String.format("End processing payment for account %s. Error info: %s", transfer.getIBAN(), transfer.getErrorInfo()));
			throw new ServiceException(transfer);
		}
		
		transfer.setStatus(TransferStatus.IN_PROGRESS);
		
		switch(transfer.getType()) {		
			case CREDIT_TRANSFER_IN:
			case DIRECT_DEBIT_OUT:	
				service.processCreditTransfer(transfer);
				break;
			case CREDIT_TRANSFER_OUT:
			case DIRECT_DEBIT_IN:
				service.processDebitTransfer(transfer);
				break;
			default:
				break;
		}
		
		LOG.info("End processing payment transfer for account " + iban);
		transfer.setStatus(TransferStatus.COMPLETED);
		return Response.status(Response.Status.OK).entity(transfer).build();
	}
}