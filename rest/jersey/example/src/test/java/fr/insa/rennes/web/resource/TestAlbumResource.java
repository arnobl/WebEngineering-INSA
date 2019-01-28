package fr.insa.rennes.web.resource;

import com.github.hanleyt.JerseyExtension;
import fr.insa.rennes.web.model.Album;
import fr.insa.rennes.web.model.Player;
import fr.insa.rennes.web.model.PlayerCard;
import fr.insa.rennes.web.utils.MyExceptionMapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.apache.log4j.BasicConfigurator;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAlbumResource {
	@SuppressWarnings("unused") @RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	Application configureJersey() {
		return new ResourceConfig(AlbumResource.class)
			.register(MyExceptionMapper.class)
			.property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeAll
	public static void beforeClass() {
		BasicConfigurator.configure();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.WARN);
	}

	@Test
	void testPostAlbum(final WebTarget target) {
		final Response responseMsg = target
			.path("album/album")
			.request()
			.post(Entity.text(""));

		final Album albumWithID = responseMsg.readEntity(Album.class);

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertEquals(1, albumWithID.getId());
	}

	@Test
	void testPostANewAlbum(final WebTarget target) {
		target.path("album/album").request().post(Entity.text(""));

		final Response responseMsg = target
			.path("album/album")
			.request()
			.post(Entity.text(""));

		final Album albumWithID = responseMsg.readEntity(Album.class);

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertEquals(2, albumWithID.getId());
	}

	@Test
	void testNoAlbumNoPlayer(final WebTarget target) {
		final Response responseMsg = target
			.path("album/player")
			.request()
			.post(Entity.xml(new Player("Raymond")));

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseMsg.getStatus());
	}

	@Test
	void testGetPlayerNothing(final WebTarget target) {
		final Response responseAfterPost = target
			.path("album/player/foo")
			.request()
			.get();

		// This awful code is required to get a list.
		final List<Player> players = responseAfterPost.readEntity(new GenericType<List<Player>>(){});

		assertTrue(players.isEmpty());
	}

	@Test
	void testChangePlayerNameKO(final WebTarget target) {
		final Response res = target
			.path("album/player/3/Robert")
			.request()
			.put(Entity.text(""));

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
	}


	@Test
	void testPostPlayerOK(final WebTarget target) {
		target.path("album/album").request().post(Entity.text(""));
		// Creation of a player.
		final Player player = new Player("Raymond");
		// Asks the addition of the player object to the server.
		// the URI "album/player" first identifies the resource class ("album") to which the request will be sent.
		// "player" permits the identification of the resource method that will process the request.
		// post(...) corresponds to the HTTP verb POST.
		// To POST an object to the server, this object must be serialised into a standard format: XML and JSON
		// Jersey provides operations (Entity.xml(...)) and processes to automatically serialised objects.
		// To do so (for both XML and Json), the object's class must be tagged with the annotation @XmlRootElement.
		// A Response object is returned by the server.
		final Response responseMsg = target
			.path("album/player")
			.request()
			.post(Entity.xml(player));

		// The Response object may also embed an object that can be read (give the expected class as parameter).
		final Player playerWithID = responseMsg.readEntity(Player.class);

		// This Response object provides a status that can be checked (see the HTTP header status picture in the subject).
		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		// The two Players instances must be equals (but their ID that are not compared in the equals method).
		assertEquals(player, playerWithID);
		// But their ID will differ since the instance returned by the server has been serialised in the database and thus
		// received a unique ID (see the JPA practice session).
		assertNotEquals(player.getId(), playerWithID.getId());
	}

	@Test
	void testGetPlayerOK(final WebTarget target) {
		target.path("album/album").request().post(Entity.text(""));
		final Player player = target
			.path("album/player")
			.request()
			.post(Entity.xml(new Player("Raymond")))
			.readEntity(Player.class);

		final Response responseAfterPost = target
			.path("album/player/" + player.getName())
			.request()
			.get();

		// This awful code is required to get a list.
		final List<Player> players = responseAfterPost.readEntity(new GenericType<List<Player>>(){});

		assertEquals(1, players.size());
		assertEquals(player, players.get(0));
	}

	@Test
	void testChangePlayerNameOK(final WebTarget target) {
		target.path("album/album").request().post(Entity.text(""));
		final Player player = target
			.path("album/player")
			.request()
			.post(Entity.xml(new Player("Raymond")))
			.readEntity(Player.class);

		final Player playerWithNewName = target
			.path("album/player/" + player.getId() + "/Robert")
			.request()
			.put(Entity.text(""))
			.readEntity(Player.class);

		assertEquals("Robert", playerWithNewName.getName());
		assertEquals(player.getId(), playerWithNewName.getId());
	}

	@Test
	public void testPostPlayercard(final WebTarget target) throws UnsupportedEncodingException {
		target.path("album/album").request().post(Entity.text(""));
		final Player player = target
			.path("album/player")
			.request()
			.post(Entity.json(new Player("Raymond")))
			.readEntity(Player.class);

		final Response res = target
			.path("album/playercard/" +
				player.getId() + "/" +
				URLEncoder.encode(LocalDateTime.of(2019, 1, 23, 12, 0).
					format(DateTimeFormatter.ISO_DATE_TIME), "UTF-8") + "/" +
				URLEncoder.encode("pic/gernot.jpg", "UTF-8") + "/" +
				URLEncoder.encode("pic/foo.jpg", "UTF-8"))
			.request()
			.post(Entity.text(""));

		final PlayerCard card = res.readEntity(PlayerCard.class);

		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
		assertNotNull(card);
		assertTrue(card.getId() > 0);
	}

	@Test
	void testDeletePlayerCard(final WebTarget target) throws UnsupportedEncodingException {
		target.path("album/album").request().post(Entity.text(""));
		final Player player = target.path("album/player").request().post(Entity.json(new Player("Raymond"))).readEntity(Player.class);
		final PlayerCard card = target.path("album/playercard/" +
				player.getId() + "/" +
				URLEncoder.encode(LocalDateTime.of(2019, 1, 23, 12, 0).
					format(DateTimeFormatter.ISO_DATE_TIME), "UTF-8") + "/" +
				URLEncoder.encode("pic/gernot.jpg", "UTF-8") + "/" +
				URLEncoder.encode("pic/foo.jpg", "UTF-8"))
			.request()
			.post(Entity.text("")).readEntity(PlayerCard.class);

		final Response res = target.path("album/playercard/" + card.getId()).request().delete();

		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	void testDeletePlayerCardKO(final WebTarget target) throws UnsupportedEncodingException {
		target.path("album/album").request().post(Entity.text(""));
		final Player player = target.path("album/player").request().post(Entity.json(new Player("Raymond"))).readEntity(Player.class);
		target.path("album/playercard/" +
				player.getId() + "/" +
				URLEncoder.encode(LocalDateTime.of(2019, 1, 23, 12, 0).
					format(DateTimeFormatter.ISO_DATE_TIME), "UTF-8") + "/" +
				URLEncoder.encode("pic/gernot.jpg", "UTF-8") + "/" +
				URLEncoder.encode("pic/foo.jpg", "UTF-8"))
			.request()
			.post(Entity.text(""));

		final Response res = target.path("album/playercard/15146846").request().delete();

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
	}
}
