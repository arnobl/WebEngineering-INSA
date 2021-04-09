package fr.insa.rennes.web;

import fr.insa.rennes.web.resource.AlbumResource;
import fr.insa.rennes.web.resource.PlayerCardResource;
import fr.insa.rennes.web.resource.PlayerResource;
import fr.insa.rennes.web.utils.DBFactory;
import fr.insa.rennes.web.utils.MyExceptionMapper;
import java.io.IOException;
import java.net.URI;
import javax.persistence.EntityManager;
import org.apache.log4j.BasicConfigurator;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class Main {
	private Main() {
		super();
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer(final String httpAddress) {
		final ResourceConfig rc = new ResourceConfig(AlbumResource.class, PlayerResource.class, PlayerCardResource.class)
			.register(MyExceptionMapper.class)
			.register(new AbstractBinder() {
				@Override
				protected void configure() {
					bindFactory(DBFactory.class).to(EntityManager.class);
				}
			})
//			.register(MoxyJsonFeature.class)
			.register(io.swagger.jaxrs.listing.ApiListingResource.class)
			.register(io.swagger.jaxrs.listing.SwaggerSerializers.class)
			.property("jersey.config.client.httpUrlConnection.setMethodWorkaround",true);

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(httpAddress), rc);
	}

	// http://localhost:4444/swagger.json to get the REST API in the JSON format
	// http://localhost:4444/swag/index.html
	public static void main(final String[] args) throws IOException, InterruptedException {
		final var adr = args.length > 0 ? args[0] : "http://localhost:4444/";

		BasicConfigurator.configure();
		final HttpServer server = startServer(adr);
		// Required to access the web pages stored in the webapp folder.
		final ClassLoader loader = Main.class.getClassLoader();
		final CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "swag/");
		docsHandler.setFileCacheEnabled(false);
		server.getServerConfiguration().addHttpHandler(docsHandler, "/swag/");
		Thread.currentThread().join();
	}
}

