package org.mdtech.moneymover.controller;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mdtech.moneymover.utils.ApplicationBinder;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;

public class TransferControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
    	ResourceConfig rc = new ResourceConfig(TransferController.class);
    	rc.packages("org.mdtech.moneymover");
    	rc.register(new ApplicationBinder());
    	rc.register(GensonJsonConverter.class);
    	return rc;
    }
    
    @Test
    public void testPass() {
    	Response output = target("/transfer/ddin/LU120010001234567891/6").request().get();
		assertThat(output.getStatus()).isEqualTo(200);
		assertThat(output.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
		assertThat(output.readEntity(String.class)).isEqualTo("{\"amount\":6,\"errorInfo\":null,\"iBAN\":\"LU120010001234567891\",\"status\":\"COMPLETED\",\"type\":\"DIRECT_DEBIT_IN\"}");
    }
    
    @Test
    public void testIncorrectTransferType() {
    	Response output = target("/transfer/blablabla/LU120010001234567891/6").request().get();
		assertThat(output.getStatus()).isEqualTo(400);
		assertThat(output.readEntity(String.class)).isEqualTo("{\"amount\":6,\"errorInfo\":\"Unknown transfer type\",\"iBAN\":\"LU120010001234567891\",\"status\":\"FAILED\",\"type\":\"UNKNOWN\"}");
    }
    
    @Test
    public void testIncorrectIBAN() {
    	Response output = target("/transfer/ddin/blablabla/6").request().get();
		assertThat(output.getStatus()).isEqualTo(400);
		assertThat(output.readEntity(String.class)).isEqualTo("{\"amount\":6,\"errorInfo\":\"Account not found in repository\",\"iBAN\":\"blablabla\",\"status\":\"FAILED\",\"type\":\"DIRECT_DEBIT_IN\"}");
    }
    
    @Test
    public void testIncorrectNegativeAmount() {
    	Response output = target("/transfer/ddin/LU120010001234567891/-6").request().get();
		assertThat(output.getStatus()).isEqualTo(400);
		assertThat(output.readEntity(String.class)).isEqualTo("{\"amount\":-6,\"errorInfo\":\"Negative amount\",\"iBAN\":\"LU120010001234567891\",\"status\":\"FAILED\",\"type\":\"DIRECT_DEBIT_IN\"}");
		
    }
  
}