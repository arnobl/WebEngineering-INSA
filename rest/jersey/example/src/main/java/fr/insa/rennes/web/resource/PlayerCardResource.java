package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Picture;
import fr.insa.rennes.web.model.Player;
import fr.insa.rennes.web.model.PlayerCard;
import fr.insa.rennes.web.utils.LocalDateXmlAdapter;
import io.swagger.annotations.Api;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("card")
@Api(value = "card")
public class PlayerCardResource {
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private final EntityManager em;

	// We give (inject) the database using the constructor of the resource
	// So: the service does not have to manage the database (not its business)
	@Inject
	public PlayerCardResource(final EntityManager em) {
		super();
		this.em = em;
	}

	@POST
	@Path("playercard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// Regarding the class PlayerCardDTO, look at the PlayerCardDTO.java
	// file for explanations. This route can work with a PlayerCard as argument;
	// the use of PlayerCardDTO is just an example to illustrate what is a DTO.
	public PlayerCard postPlayerCard(final PlayerCardDTO cardDTO) {
		final Player player = em.find(Player.class, cardDTO.id);

		if(player == null) {
			throw new WebApplicationException("no player", HttpURLConnection.HTTP_BAD_REQUEST);
		}

		final PlayerCard card = new PlayerCard(player,
			new Picture(cardDTO.img), new Picture(cardDTO.img), new LocalDateXmlAdapter().unmarshal(cardDTO.date));

		final EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(card);
			tr.commit();
			LOGGER.info("Card added: " + card);
			return card;
		}catch(final IllegalStateException | PersistenceException ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, String.format("Crash on adding a playercard: %s", card), ex);
			throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}



	@DELETE
	@Path("playercard/{id}")
	public Response deletePlayerCard(@PathParam("id") final int id) {
		final EntityTransaction tr = em.getTransaction();
		try {
			final PlayerCard playerCard = em.find(PlayerCard.class, id);

			if(playerCard == null) {
				throw new WebApplicationException("no card", HttpURLConnection.HTTP_BAD_REQUEST);
			}

			tr.begin();
			em.remove(playerCard);
			tr.commit();
			playerCard.getAlbum().removeCard(playerCard);
			LOGGER.info("Card removed: " + playerCard);
		}catch(final RollbackException | IllegalStateException ex) {
			if(tr.isActive()) {
				tr.rollback();
			}
			LOGGER.log(Level.SEVERE, String.format("Crash on deleting a player card with the id: %d", id), ex);
			throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
		}

		return Response.status(HttpURLConnection.HTTP_OK).build();
	}
}
