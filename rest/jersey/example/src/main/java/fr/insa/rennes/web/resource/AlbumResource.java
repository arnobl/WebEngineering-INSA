package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Album;
import fr.insa.rennes.web.model.LocalDateXmlAdapter;
import fr.insa.rennes.web.model.Picture;
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
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
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
	/**
	 * Only one album at the same time.
	 */
	private Album album;


	public AlbumResource() {
		super();
		emf = Persistence.createEntityManagerFactory("playerCardDB");
		em = emf.createEntityManager();
	}

	@POST
	@Path("album/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAlbum() {
		final Album oldAlbum = album;
		final Album newAlbum = new Album();
		final EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(newAlbum);
			tr.commit();
			album = newAlbum;
			LOGGER.info("Album created: " + album);

			if(oldAlbum != null) {
				tr.begin();
				em.remove(oldAlbum);
				tr.commit();
				LOGGER.info("Album removed: " + oldAlbum);
			}

			return Response.status(Response.Status.OK).entity(newAlbum).build();
		}catch(final RollbackException | IllegalStateException ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on adding an album: " + newAlbum, ex);
			// A Web exception is then thrown.
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}
	}

	private void checkAlbum() {
		if(album == null) {
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, "no album").build());
		}
	}

	/*
	This is an example of how to give as arguments objects that will be marshalled.
	In real life, this is rarely the case:
	Data that describe the object to add are given as arguments instead of the object itself
	(the front-end may not know the business logic of the application, ie the classes).
	See postPlayerCard for the alternative.
	 */
	@POST
	@Path("player/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_XML)
	public Response postPlayer(final Player player) {
		checkAlbum();

		final EntityTransaction tr = em.getTransaction();
		try {
			// begin starts a transaction:
			// https://en.wikibooks.org/wiki/Java_Persistence/Transactions
			tr.begin();
			em.persist(player);
			tr.commit();
			album.addPlayer(player);
			LOGGER.info("Player added: " + album);
			return Response.status(Response.Status.OK).entity(player).build();
		}catch(final RollbackException | IllegalStateException ex) {
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
	@Path("playercard/{playerid}/{date}/{front}/{back}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postPlayerCard(@PathParam("playerid") final int playerid, @PathParam("date") final String date,
		@PathParam("front") final String frontPicture, @PathParam("back") final String backPicture) {
		checkAlbum();

		final Player player = album.getPlayers()
			.stream()
			.filter(p -> p.getId() == playerid)
			.findFirst()
			.orElseThrow(() -> new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, "no player").build()));

		final PlayerCard card = new PlayerCard(player, new Picture(frontPicture),
			backPicture == null ? null : new Picture(frontPicture),
			new LocalDateXmlAdapter().unmarshal(date));

		final EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(card);
			tr.commit();
			album.addCard(card);
			LOGGER.info("Card added: " + album);
			return Response.status(Response.Status.OK).entity(card).build();
		}catch(final RollbackException | IllegalStateException ex) {
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
		// This example makes no use of the album (as it should be expected).
		// Instead, it shows how to call a predefined query over the underlying database
		return em
			.createNamedQuery("getPlayerWithName", Player.class)
			.setParameter("name", name)
			.getResultList();
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
		}catch(final RollbackException | IllegalStateException ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on setting the name of a player with id: " + id + " and the new name: " + newName, ex);
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, "cannot add").build());
		}catch(final NoResultException ex) {
			LOGGER.log(Level.SEVERE, "The id may not be a good one: " + id, ex);
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, "invalid request").build());
		}
	}

	@DELETE
	@Path("playercard/{id}")
	public Response deletePlayerCard(@PathParam("id") final String id) {
		final EntityTransaction tr = em.getTransaction();
		try {
			final int idint = Integer.valueOf(id);
			final PlayerCard playerCard = album
				.getCards()
				.parallelStream()
				.filter(card -> card.getId() == idint)
				.findAny()
				.orElseThrow(() -> new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, "no card").build()));

			tr.begin();
			em.remove(playerCard);
			tr.commit();
			album.removeCard(playerCard);
			LOGGER.info("Card removed: " + album);
		}catch(final RollbackException | IllegalStateException ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, "Crash on deleting a player card with the id: " + id, ex);
			throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).build());
		}

		return Response.status(HttpURLConnection.HTTP_OK).build();
	}
}
