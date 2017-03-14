package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Album;
import fr.insa.rennes.web.model.Player;
import io.swagger.annotations.Api;
import java.net.HttpURLConnection;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("album")
@Api(value = "/album", description = "Operations on the album")
public class AlbumResource {
	private final EntityManagerFactory emf;
	private final EntityManager em;
	private final Album album;


	public AlbumResource() {
		super();
		album = new Album();
		emf = Persistence.createEntityManagerFactory("playerCardDB");
		em = emf.createEntityManager();
	}

	protected void flush() {
		em.clear();
		em.close();
		emf.close();
	}

	@Override
	protected void finalize() throws Throwable {
		flush();
		super.finalize();
	}


	// curl -H "Content-Type: application/json" -d '{"name":"blouin"}' -X POST "http://localhost:8080/api/album/player"
	// -> {"type":"player","id":1,"name":"blouin"}
	@POST
	@Path("player/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response postPlayer(final Player player) {
		try {
			// begin starts a transaction:
			// https://en.wikibooks.org/wiki/Java_Persistence/Transactions
			em.getTransaction().begin();
			em.persist(player);
			em.getTransaction().commit();
			return Response.status(Response.Status.OK).entity(player).build();
		}catch(Throwable ex) {
			// If an exception occurs, the transaction has to be rollbacked.
			em.getTransaction().rollback();
			// A Web exception is then thrown.
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}
	}
}
