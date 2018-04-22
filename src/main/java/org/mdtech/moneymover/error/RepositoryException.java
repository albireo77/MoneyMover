package org.mdtech.moneymover.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RepositoryException extends WebApplicationException {
	
	public RepositoryException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.APPLICATION_JSON).build());
    }
}
