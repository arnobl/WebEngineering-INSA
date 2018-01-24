package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Album;
import fr.insa.rennes.web.model.Player;
import fr.insa.rennes.web.model.PlayerCard;
import io.swagger.annotations.Api;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("album")
@Api(value = "album", description = "Operations on the album")
public class AlbumResource {
	private static final Logger LOGGER = Logger.getAnonymousLogger();

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


	@POST
	@Path("player/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response postPlayer(final Player player) {
		final EntityTransaction tr = em.getTransaction();
		try {
			// begin starts a transaction:
			// https://en.wikibooks.org/wiki/Java_Persistence/Transactions
			tr.begin();
			em.persist(player);
			tr.commit();
			return Response.status(Response.Status.OK).entity(player).build();
		}catch(final Throwable ex) {
			// If an exception occurs after a begin and before a commit (isActive), the transaction has to be rollbacked.
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on adding a player: " + player, ex);
			// A Web exception is then thrown.
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}
	}

	@POST
	@Path("playercard/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response postPlayerCard(final PlayerCard card) {
		final EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(card);
			tr.commit();
			return Response.status(Response.Status.OK).entity(card).build();
		}catch(final Throwable ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on adding a playercard: " + card, ex);
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}
	}

	@GET
	@Path("player/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player> getPlayers(@PathParam("name") final String name) {
		return em.createNamedQuery("getPlayerWithName", Player.class).setParameter("name", name).getResultList();
	}


	@PUT
	@Path("player/{id}/{newname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Player setPlayerName(@PathParam("id") final String id, @PathParam("newname") final String newName) {
		final EntityTransaction tr = em.getTransaction();
		try {
			final Player player = em.createQuery("SELECT p FROM Player p WHERE p.id=:id", Player.class).setParameter("id", Integer.valueOf(id)).getSingleResult();
			tr.begin();
			player.setName(newName);
			tr.commit();
			return player;
		}catch(final Throwable ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on setting the name of a player with id: " + id + " and the new name: " + newName, ex);
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}
	}

	@DELETE
	@Path("player/{id}")
	public Response deletePlayer(@PathParam("id") final String id) {
		final EntityTransaction tr = em.getTransaction();
		try {
			final Player player = em.find(Player.class, Integer.valueOf(id));
			tr.begin();
			em.remove(player);
			tr.commit();
			return Response.status(Response.Status.OK).build();
		}catch(final Throwable ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on deleting a player with the id: " + id, ex);
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}
	}

	@DELETE
	@Path("playercard/{id}")
	public void deletePlayerCard(@PathParam("id") final String id) {
		final EntityTransaction tr = em.getTransaction();
		try {
			final int idint = Integer.valueOf(id);
			final PlayerCard playerCard = album.getCards().parallelStream().filter(card -> card.getId() == idint).findAny().
				orElseThrow(() -> new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build()));
			tr.begin();
			em.remove(playerCard);
			tr.commit();
		}catch(final Throwable ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on deleting a player card with the id: " + id, ex);
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}
	}
}
