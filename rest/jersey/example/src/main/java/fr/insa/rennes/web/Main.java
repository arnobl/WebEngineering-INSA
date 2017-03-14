package fr.insa.rennes.web;

import fr.insa.rennes.web.resource.AlbumResource;
import fr.insa.rennes.web.utils.MyExceptionMapper;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "api/";
	public static final String HTTP_ADDRESS = "http://localhost:8080/" + BASE_URI;

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig(AlbumResource.class).
			packages("fr.insa.rennes.web.resource").
			register(MyExceptionMapper.class).
			register(io.swagger.jaxrs.listing.ApiListingResource.class).
			register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(HTTP_ADDRESS), rc);
	}

	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		// Required to access the web pages stored in the webapp folder.
		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/main/webapp"), "/myFirstWebApp/");
		ClientBuilder.newClient().target(HTTP_ADDRESS);
		System.in.read();
		server.shutdownNow();
	}
}

