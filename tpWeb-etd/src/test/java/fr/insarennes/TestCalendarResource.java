package fr.insarennes;

import fr.insarennes.model.Enseignant;
import fr.insarennes.resource.CalendarResource;
import fr.insarennes.utils.MyExceptionMapper;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestCalendarResource extends JerseyTest {
	// @Inject permits to get the CalendarResource singleton created by Jersey (somehow line 29).
	@Inject private CalendarResource calResource;

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		// You must register the service you want to test
		// register(this) is just used to allow this class to access the CalendarResource instance.
		return new ResourceConfig(CalendarResource.class).register(this).register(MyExceptionMapper.class);
	}

	@Override
	@After // The @After annotation permits to tag methods to be executed after each test. This method is usually called tearDown.
	public void tearDown() throws Exception {
		super.tearDown();
		// It is necessary to flush the database between each test to avoid side-effects.
		calResource.flush();
	}

	@Test
	public void testPostEnseignantOK() {
		// Creation of a teacher.
		Enseignant ensWithoutID = new Enseignant("Cellier");
		// Asks the addition of the teacher object to the server.
		// target(...) is provided by the JerseyTest class to ease the writting of the tests
		// the URI "calendar/ens" first identifies the service ("calendar") to which the request will be sent.
		// "ens" permits the identification of the server operation that will process the request.
		// post(...) corresponds to the HTTP verb POST.
		// To POST an object to the server, this object must be serialised into a standard format: XML and JSON
		// Jersey provides operations (Entity.xml(...)) and processes to automatically serialised objects.
		// To do so (for both XML and Json), the object's class must be tagged with the annotation @XmlRootElement (see Enseignant.java)
		// A Response object is returned by the server.
		Response responseAfterPost = target("calendar/ens").request().post(Entity.xml(ensWithoutID));
		// This Response object provides a status that can be checked (see the HTTP header status picture in the subject).
		assertEquals(Response.Status.OK.getStatusCode(), responseAfterPost.getStatus());
		// The Response object may also embed an object that can be read (give the expected class as parameter).
		Enseignant ensWithID = responseAfterPost.readEntity(Enseignant.class);
		// The two Enseignant instances must be equals.
		assertEquals(ensWithoutID, ensWithID);
		// But their ID will differ since the instance returned by the server has been serialised in the database and thus
		// received a unique ID (see the JPA practice session).
		assertNotSame(ensWithoutID.getId(), ensWithID.getId());
	}

	// In your tests, do not create teachers, topics, and courses that already exist (in the constructor of the CalendarResource).
}
