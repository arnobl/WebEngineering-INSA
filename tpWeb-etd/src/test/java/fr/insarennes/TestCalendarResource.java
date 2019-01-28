package fr.insarennes;

import com.github.hanleyt.JerseyExtension;
import fr.insarennes.model.Enseignant;
import fr.insarennes.resource.CalendarResource;
import fr.insarennes.utils.MyExceptionMapper;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.apache.log4j.BasicConfigurator;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCalendarResource {
	@RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	Application configureJersey() {
		return new ResourceConfig(CalendarResource.class)
			.register(MyExceptionMapper.class)
			.property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeAll
	public static void beforeClass() {
		BasicConfigurator.configure();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.WARN);
	}

	@Test
	void testPostEnseignantOK(final WebTarget target) {
		// Asks the addition of the teacher object to the server.
		// target(...) is provided by the JerseyTest class to ease the writting of the tests
		// the URI "calendar/ens" first identifies the service ("calendar") to which the request will be sent.
		// "ens" permits the identification of the server operation that will process the request.
		// post(...) corresponds to the HTTP verb POST.
		// To POST an object to the server, this object must be serialised into a standard format: XML and JSON
		// Jersey provides operations (Entity.xml(...)) and processes to automatically serialised objects.
		// To do so (for both XML and Json), the object's class must be tagged with the annotation @XmlRootElement (see Enseignant.java)
		// A Response object is returned by the server.
		Response responseAfterPost = target
			.path("calendar/ens/Cellier")
			.request()
			.post(Entity.text(""));
		// This Response object provides a status that can be checked (see the HTTP header status picture in the subject).
		assertEquals(Response.Status.OK.getStatusCode(), responseAfterPost.getStatus());
		// The Response object may also embed an object that can be read (give the expected class as parameter).
		Enseignant ensWithID = responseAfterPost.readEntity(Enseignant.class);
		// The two Enseignant instances must be equals.
		assertEquals("Cellier", ensWithID.getName());
		// But their ID will differ since the instance returned by the server has been serialised in the database and thus
		// received a unique ID (see the JPA practice session).
		assertTrue(ensWithID.getId() > 0);
	}

	// In your tests, do not create teachers, topics, and courses that already exist (in the constructor of the CalendarResource).
}
