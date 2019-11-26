package org.mdtech.moneymover.controller;

import org.apache.log4j.Logger;
import org.mdtech.moneymover.domain.BalanceRequest;
import org.mdtech.moneymover.service.BalanceService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Path("/balance")
public class BalanceController {

	private static final Logger LOG = Logger.getLogger(BalanceController.class);

	private final BalanceService service;

	@Inject
	public BalanceController(BalanceService service) {
		this.service = service;
	}
 
	@Path("/{account}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response processBalance(@PathParam("account") String account) {
		
		LOG.info("Start processing balance request for account " + account);
		
		BalanceRequest request = new BalanceRequest();
		request.setAccount(account);

		service.processRequest(request);
		
		LOG.info("End processing balance request");
		return Response.status(Response.Status.OK).entity(request).build();
	}
}