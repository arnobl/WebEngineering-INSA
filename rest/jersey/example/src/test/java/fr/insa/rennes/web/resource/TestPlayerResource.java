package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Player;
import fr.insa.rennes.web.model.PlayerCard;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPlayerResource extends TestResource {
	@Test
	@DisplayName("Posting a player in  album should work")
	void testPostPlayerOK(final WebTarget target) {
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
			.path("player/player")
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
	@DisplayName("Crashing the database while posting a new player should be supported")
	void testPostPlayerCrashDBOkAfter(final WebTarget target) {
		em.getTransaction().begin();
		final Response responseMsg = target
			.path("player/player")
			.request()
			.post(Entity.xml(new Player("Raymond")));

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseMsg.getStatus());
		assertNull(responseMsg.readEntity(Player.class));
	}


	@Test
	@DisplayName("Crashing the database while posting a new player should be supported")
	void testPostPlayerCrashWithNull(final WebTarget target) {
		final Response responseMsg = target
			.path("player/player")
			.request()
			.post(Entity.xml(new Player(null)));

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseMsg.getStatus());
		assertNull(responseMsg.readEntity(Player.class));
	}


	@Test
	@DisplayName("Crashing the database while posting a new player shoud not block the app")
	void testPostPlayerCrashDBOKAfter(final WebTarget target) {
		final Player player = new Player("Raymond");
		em.getTransaction().begin();
		target
			.path("player/player")
			.request()
			.post(Entity.xml(player));

		final Response responseMsg = target
			.path("player/player")
			.request()
			.post(Entity.xml(player));

		final Player playerWithID = responseMsg.readEntity(Player.class);

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertEquals(player, playerWithID);
		assertNotEquals(player.getId(), playerWithID.getId());
	}

	@Test
	@DisplayName("Getting the players when no player should return en empty list")
	void getPlayersWithEmptyAlbum(final WebTarget target) {
		final Response responseMsg = target
			.path("player/players")
			.request()
			.get();

		final List<Player> players = responseMsg.readEntity(new GenericType<>(){});
		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertTrue(players.isEmpty());
	}

	@Nested
	class WithPlayer {
		Player player;

		@BeforeEach
		void setUp(final WebTarget target) {
			player = target
				.path("player/player")
				.request()
				.post(Entity.xml(new Player("Raymond")))
				.readEntity(Player.class);
		}

		@Test
		@DisplayName("Getting all the players should work")
		void testGetPlayerOK(final WebTarget target) {
			final Response responseAfterPost = target
				.path("player/players/" + player.getName())
				.request()
				.get();

			// This awful code is required to get a list.
			final List<Player> players = responseAfterPost.readEntity(new GenericType<>() {});

			assertEquals(List.of(player), players);
		}


		@Test
		@DisplayName("Changing the name of a player that does not exist should be supported")
		void testChangePlayerNameKO(final WebTarget target) {
			final Response res = target
				.path("player/player/30/Robert")
				.request()
				.put(Entity.text(""));

			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
		}

		@Test
		@DisplayName("Changing the name of a player that exists should work")
		void testChangePlayerNameOK(final WebTarget target) {
			final Player playerWithNewName = target
				.path("player/player/" + player.getId() + "/Robert")
				.request()
				.put(Entity.text(""))
				.readEntity(Player.class);

			assertEquals("Robert", playerWithNewName.getName());
			assertEquals(player.getId(), playerWithNewName.getId());
		}

		@Test
		@DisplayName("Crashing the database while changing the name you be managed")
		void testChangePlayerNameCrashOK(final WebTarget target) {
			em.getTransaction().begin();
			final Response res = target
				.path("player/player/" + player.getId() + "/Robert")
				.request()
				.put(Entity.text(""));

			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
			assertNull(res.readEntity(PlayerCard.class));
		}

		@Test
		@DisplayName("Changing the name of a player after a crash should work")
		void testChangePlayerNameAfterCrashOK(final WebTarget target) {
			em.getTransaction().begin();
			target
				.path("player/player/" + player.getId() + "/Robert")
				.request()
				.put(Entity.text(""));

			final Player playerWithNewName = target
				.path("player/player/" + player.getId() + "/Robert")
				.request()
				.put(Entity.text(""))
				.readEntity(Player.class);

			assertEquals("Robert", playerWithNewName.getName());
			assertEquals(player.getId(), playerWithNewName.getId());
		}


		@Disabled
		@Test
		@DisplayName("Changing the name of a player that does not exist should be supported")
		void testPatchPlayerKO(final WebTarget target) {
			final Response res = target
				.path("player/player/")
				.request()
				.build("PATCH", Entity.json(new Player("Foo")))
				.invoke();

			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
		}

		@Disabled
		@Test
		@DisplayName("Changing the name of a player that exists should work")
		void testpatchPlayerOK(final WebTarget target) {
			player.setName("Robert");

			final Player playerWithNewName = target
				.path("player/player")
				.request()
				.build("PATCH", Entity.json(player))
				.invoke()
				.readEntity(Player.class);

			assertEquals("Robert", playerWithNewName.getName());
			assertEquals(player.getId(), playerWithNewName.getId());
		}


		@Disabled
		@Test
		@DisplayName("Crashing the database while changing the name should be supported")
		void testpatchPlayerCrashOK(final WebTarget target) {
			player.setName("Robert");

			em.getTransaction().begin();
			final Response res = target
				.path("player/player")
				.request()
				.build("PATCH", Entity.json(player))
				.invoke();

			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
		}
	}
}
