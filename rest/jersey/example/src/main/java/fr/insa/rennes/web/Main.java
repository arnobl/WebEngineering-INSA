package fr.insa.rennes.web;

import fr.insa.rennes.web.resource.AlbumResource;
import fr.insa.rennes.web.utils.MyExceptionMapper;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public final class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String HTTP_ADDRESS = "http://localhost:4444/";

	private Main() {
		super();
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig(AlbumResource.class)
			.register(MyExceptionMapper.class)
			.register(MoxyJsonFeature.class)
			.register(io.swagger.jaxrs.listing.ApiListingResource.class)
			.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(HTTP_ADDRESS), rc);
	}

	// http://localhost:4444/swagger.json to get the REST API in the JSON format
	// http://localhost:4444/swag/index.html
	public static void main(final String[] args) throws IOException {
		final HttpServer server = startServer();
		// Required to access the web pages stored in the webapp folder.
		final ClassLoader loader = Main.class.getClassLoader();
		final CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "swag/");
		docsHandler.setFileCacheEnabled(false);

		server.getServerConfiguration().addHttpHandler(docsHandler, "/swag/");
		ClientBuilder.newClient().target(HTTP_ADDRESS);
		System.in.read();
		server.shutdownNow();
	}
}

