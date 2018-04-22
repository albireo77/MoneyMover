package org.mdtech.moneymover.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mdtech.moneymover.domain.Transfer;

public class ServiceException extends WebApplicationException {
	
	public ServiceException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.APPLICATION_JSON).build());
    }
	
	public ServiceException(Transfer transfer) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(transfer).type(MediaType.APPLICATION_JSON).build());
    }
}
