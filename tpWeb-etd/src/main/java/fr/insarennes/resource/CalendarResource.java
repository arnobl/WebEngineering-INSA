package fr.insarennes.resource;

import fr.insarennes.model.Agenda;
import fr.insarennes.model.Enseignant;
import io.swagger.annotations.Api;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.BasicConfigurator;

@Singleton
@Path("calendar")
@Api(value = "calendar")
public class CalendarResource {
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	// Static blocks are used to parametrized static objects of the class.
	static {
		// Define the level of importance the Logger has to consider.
		// The logged messages with an importance lower than the one defined here will be ignored.
		LOGGER.setLevel(Level.ALL);

		BasicConfigurator.configure();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.WARN);
	}

	private final Agenda agenda;

	public CalendarResource() {
		super();
		agenda = new Agenda();

//	 	You can add here calendar elements to add by default in the database of the application.
//	 	For instance:
//			Enseignant ens = new Enseignant("Blouin");
//			Matiere mat = new Matiere("Web", 3);
//
//			TD td = new TD(mat, LocalDate.of(2015, Month.JANUARY, 2).atTime(8, 0), ens, Duration.ofHours(2));
//			agenda.addCours(td);
//
//			LOGGER.log(Level.INFO, "Added during the creation of the calendar resource:");
//			LOGGER.log(Level.INFO, "a Enseignant: " + ens);
//			LOGGER.log(Level.INFO, "a Matiere: " + mat);
//			LOGGER.log(Level.INFO, "a TD: " + td);
	}


	//curl -X POST "http://localhost:8080/calendar/ens/Foo"
	@POST
	@Path("ens/{name}")
	@Produces(MediaType.APPLICATION_XML)
	public Enseignant postEnseignant(@PathParam("name") final String name) {
		final Enseignant ens = new Enseignant(name);
		try {
			agenda.addEnseignant(ens);
		}catch(final IllegalArgumentException ex) {
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, ex.getMessage()).build());
		}
		return ens;
	}


	// Do not use @Consumes when no data are sent

	// When adding a course (@POST a course), do not forget to add it to the agenda as well:
	// agenda.addCours(c);

	// When getting the list of courses for a given week, do not use a SQL command but agenda.getCours();

	// When getting the list of courses for a given week, the Cours class already has a function matchesID(int) that checks whether the given ID is used by the course.
}
