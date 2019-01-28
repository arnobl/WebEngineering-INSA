package fr.insarennes.resource;

import fr.insarennes.model.Agenda;
import fr.insarennes.model.Enseignant;
import io.swagger.annotations.Api;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.BasicConfigurator;

@Singleton // Q: with and without, see 3.4 https://jersey.java.net/documentation/latest/jaxrs-resources.html
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

	private final EntityManagerFactory emf;
	private final EntityManager em;
	private final Agenda agenda;

	public CalendarResource() {
		super();
		agenda = new Agenda();
		emf = Persistence.createEntityManagerFactory("agendapp");
		em = emf.createEntityManager();

		final EntityTransaction tr = em.getTransaction();

		tr.begin();
		em.persist(agenda);
		tr.commit();

		// You can add here calendar elements to add by default in the database of the application.
		// For instance:
		//		try {
		//			// Each time you add an object into the database or modify an object already added into the database,
		//			// You must surround your code with a em.getTransaction().begin() that identifies the beginning of a transaction
		//			// and a em.getTransaction().commit() at the end of the transaction to validate it.
		//			// In case of crashes, you have to surround the code with a try/catch block, where the catch rollbacks the
		//			// transaction using em.getTransaction().rollback()
		//			tr.begin();
		//
		//			Enseignant ens = new Enseignant("Blouin");
		//			Matiere mat = new Matiere("Web", 3);
		//
		//			em.persist(ens);
		//			em.persist(mat);
		//
		//			TD td = new TD(mat, LocalDate.of(2015, Month.JANUARY, 2).atTime(8, 0), ens, Duration.ofHours(2));
		//			agenda.addCours(td);
		//			em.persist(td);
		//			tr.commit();
		//
		//			LOGGER.log(Level.INFO, "Added during the creation of the calendar resource:");
		//			LOGGER.log(Level.INFO, "a Enseignant: " + ens);
		//			LOGGER.log(Level.INFO, "a Matiere: " + mat);
		//			LOGGER.log(Level.INFO, "a TD: " + td);
		//		}catch(final RollbackException | IllegalStateException ex) {
		//			LOGGER.log(Level.SEVERE, "Crash during the creation of initial data", ex);
		//			if(tr.isActive()) {
		//				tr.rollback();
		//			}
		//		}
	}


	public void flush() {
		em.clear();
		em.close();
		emf.close();
	}

	//curl -H "Content-Type: application/json" -d '{"name":"blouin"}' -X POST "http://localhost:8080/calendar/ens"
	// To know the XML format, look at the returned XML message.
	@POST
	@Path("ens/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Enseignant postEnseignant(@PathParam("name") final String name) {
		final EntityTransaction tr = em.getTransaction();
		try {
			final Enseignant ens = new Enseignant(name);
			// begin starts a transaction:
			// https://en.wikibooks.org/wiki/Java_Persistence/Transactions
			tr.begin();
			em.persist(ens);
			tr.commit();
			return ens;
		}catch(final RollbackException | IllegalStateException ex) {
			// If an exception occurs after a begin and before the commit, the transaction has to be rollbacked.
			if(tr.isActive()) {
				tr.rollback();
			}
			// Loggers are widely used to log information about the execution of a program.
			// The classical use is a static final Logger for each class or for the whole application.
			// Here, the first parameter is the level of importance of the message.
			// The second parameter is the message, and the third one is the exception.
			// Various useful methods compose a Logger.
			// By default, when a message is logged it is printed in the console.
			LOGGER.log(Level.SEVERE, "Crash on adding a Enseignant: " + name, ex);
			// A Web exception is then thrown.
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, "Cannot persist").build());
		}catch(final NullPointerException ex) {
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, "The name is not correct").build());
		}
	}


	// DO NOT USE begin(), commit() or rollback() for the @GET verb.

	// When modifying an object (@PUT verb) DO NOT USE em.persits(obj) again since the object has been already added to the database during its @POST

	// Do not use @Consumes when no data are sent

	// When adding a course (@POST a course), do not forget to add it to the agenda as well:
	// em.persist(c);
	// agenda.addCours(c);

	// When getting the list of courses for a given week, do not use a SQL command but agenda.getCours();

	// When getting the list of courses for a given week, the Cours class already has a function matchesID(int) that checks whether the given ID is used by the course.
}
