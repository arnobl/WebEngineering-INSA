package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Player;
import io.swagger.annotations.Api;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("api/player")
@Api(value = "player")
public class PlayerResource {
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private final EntityManager em;
	/**
	 * Only one album at the same time.
	 */

	// We give (inject) the database using the constructor of the resource
	// So: the service does not have to manage the database (not its business)
	@Inject
	public PlayerResource(final EntityManager em) {
		super();
		this.em = em;
	}



	/*
	This is an example of how to give as arguments objects that will be marshalled.
	In real life, this is rarely the case:
	Data that describe the object to add are given as arguments instead of the object itself
	(the front-end may not know the business logic of the application, ie the classes).
	See postPlayerCard for the alternative.
	 */
	@POST
	@Path("player")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response postPlayer(final Player pl) {
		final EntityTransaction tr = em.getTransaction();
		try {
			// begin starts a transaction:
			// https://en.wikibooks.org/wiki/Java_Persistence/Transactions
			tr.begin();
			em.persist(pl);
			tr.commit();
			LOGGER.info("Player added: " + pl);
			return Response.status(Response.Status.OK).entity(pl).build();
		}catch(final PersistenceException | IllegalStateException ex) {
			// If an exception occurs after a begin and before a commit (isActive), the transaction has to be rollbacked.
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on adding a player: " + pl, ex);
			// A Web exception is then thrown.
			throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}


	@GET
	@Path("players")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player> getPlayers() {
		try {
			return em.createQuery("SELECT p FROM Player p", Player.class).getResultList();
		}catch(final PersistenceException ex) {
			LOGGER.log(Level.SEVERE, "Crash in getPlayers", ex);
			throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}


	@GET
	@Path("players/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player> getPlayersNamedAs(@PathParam("name") final String name) {
		// This example makes no use of albums (it searches for any player in the database).
		// It shows how to call a predefined query over the underlying database
		return em
			.createNamedQuery("getPlayerWithName", Player.class)
			.setParameter("name", name)
			.getResultList();
	}


	// This REST route is quite useless as the next one (patchPlayer) can update
	// any attribute of a player object.
	// The idea of this route is to show how to modify an object simply.
	// According to the RFC 5789 (https://tools.ietf.org/html/rfc5789)
	// The PATCH verb should be used to modify an object while PUT should be used to replace
	// an object with another one.
	// Anyway, in this example we use PUT to modify the name of a player
	@PUT
	@Path("player/{id}/{newname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Player setPlayerName(@PathParam("id") final int id, @PathParam("newname") final String newName) {
		final EntityTransaction tr = em.getTransaction();
		try {
			final Player player = em.find(Player.class, id);

			if(player == null) {
				LOGGER.log(Level.SEVERE, String.format("The id may not be a good one: %d", id));
				throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
			}

			tr.begin();
			player.setName(newName);
			tr.commit();
			return player;

		}catch(final RollbackException | IllegalStateException ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, String.format("Crash on setting the name of a player with id: %d and the new name: %s", id, newName), ex);
			throw new WebApplicationException("cannot add", HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}

	// Instead of having a PUT/PATCH method for each property of an object
	// we can use PUT/PATCH to apply partial updates:
	// We take as arguments the player to update with the modified attributes
	// This object is then merged with the corresponding object in the database to update this last.
	// The benefits: can update several attributes, reduce the number of REST routes
	@PATCH
	@Path("player")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Player patchPlayer(final Player pl) {
		final EntityTransaction tr = em.getTransaction();
		try {
			if(em.find(Player.class, pl.getId()) == null) {
				throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
			}

			tr.begin();
			em.merge(pl);
			tr.commit();
			return pl;
		}catch(final RollbackException | IllegalStateException | IllegalArgumentException ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
