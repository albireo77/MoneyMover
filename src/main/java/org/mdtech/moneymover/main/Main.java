package org.mdtech.moneymover.main;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.mdtech.moneymover.utils.ApplicationBinder;
import org.mdtech.moneymover.utils.ApplicationProperties;

import com.owlike.genson.ext.jaxrs.GensonJsonConverter;

import java.io.IOException;
import java.net.URI;

public class Main {
	
	public static String BASE_URI;
	
	static {
		String host = ApplicationProperties.getProperty("hostname");
		if (host == null) {
			host = "localhost";
		}
		String port = ApplicationProperties.getProperty("port");
		if (port == null) {
			port = "8080";
		}
		BASE_URI = String.format("http://%s:%s/moneymover", host, port);
	}

	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig().packages("org.mdtech.moneymover");
		rc.register(GensonJsonConverter.class);
		rc.register(new ApplicationBinder());
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

	public static void main(String[] args) throws IOException {
		
		final HttpServer server = startServer();
		System.out.println("MoneyMover started and available at " + BASE_URI + "\nHit enter to stop it...");
		System.in.read();
		server.shutdown();
	}
}
