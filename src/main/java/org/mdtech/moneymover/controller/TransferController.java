package org.mdtech.moneymover.controller;
 
import org.apache.log4j.Logger;

import org.mdtech.moneymover.domain.Transfer;
import org.mdtech.moneymover.domain.TransferStatus;
import org.mdtech.moneymover.domain.TransferType;
import org.mdtech.moneymover.error.Error;
import org.mdtech.moneymover.service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transfer")
public class TransferController {

	private static final Logger LOG = Logger.getLogger(TransferController.class);
	
	private final TransferService service;
	
	@Inject
	public TransferController(TransferService service) {
		this.service = service;
	}
 
	@Path("/{transferCode}/{origAccount}/{destAccount}/{amount}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response processTransfer(@PathParam("transferCode") String transferCode, @PathParam("origAccount") String origAccount,
			@PathParam("destAccount") String destAccount, @PathParam("amount") String amount) {
		
		LOG.info("Start processing payment transfer from account " + origAccount);
		
		Transfer transfer = Transfer.of(transferCode, origAccount, destAccount, amount);

		List<Error> errors = service.validate(transfer);
		if (!errors.isEmpty()) {
			transfer.setStatus(TransferStatus.FAILED);
			transfer.getErrors().addAll(errors);
		} else {
			transfer.setStatus(TransferStatus.IN_PROGRESS);
			if (transfer.getType() == TransferType.CREDIT_TRANSFER) {
				service.processCreditTransfer(transfer);
			} else if (transfer.getType() == TransferType.DIRECT_DEBIT) {
				service.processDebitTransfer(transfer);
			}
			if (transfer.getErrors().isEmpty()) {
				transfer.setStatus(TransferStatus.COMPLETED);
			} else {
				transfer.setStatus(TransferStatus.FAILED);
			}
		}
		
		LOG.info("End processing payment transfer");
		return Response.status(Response.Status.OK).entity(transfer).build();
	}
}