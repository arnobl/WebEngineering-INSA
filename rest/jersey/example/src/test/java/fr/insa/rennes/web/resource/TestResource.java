package fr.insa.rennes.web.resource;

import com.github.hanleyt.JerseyExtension;
import fr.insa.rennes.web.utils.MyExceptionMapper;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import org.apache.log4j.BasicConfigurator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

abstract class TestResource {
	@SuppressWarnings("unused") @RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	// The database is an attribute to play with it in tests
	// For example to provoke errors
	EntityManager em;

	Application configureJersey() {
		em = Persistence.createEntityManagerFactory("testPlayerCardDB").createEntityManager();

		return new ResourceConfig(AlbumResource.class, PlayerResource.class)
			.register(MyExceptionMapper.class)
			.register(new AbstractBinder() {
				@Override
				protected void configure() {
					bind(em).to(EntityManager.class);
				}
			})
			.property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeAll
	static void beforeClass() {
		BasicConfigurator.configure();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.WARN);
	}

	@BeforeEach
	void setUp(final WebTarget target) {
		// Requires to use the PATCH verb
		target.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
	}

	@AfterEach
	void tearDown() {
		em.close();
	}
}
