package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Album;
import javax.persistence.EntityTransaction;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestAlbumResource extends TestResource {
	@Test
	@DisplayName("A wrong route should not be found")
	void testWrongRoute(final WebTarget target) {
		final Response responseMsg = target
			.path("api/album/album/foo")
			.request()
			.get();

		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseMsg.getStatus());
	}

	@Test
	@DisplayName("Getting an album when no one is created should return null")
	void testgetAlbumNull(final WebTarget target) {
		final Response responseMsg = target
			.path("api/album/album/1")
			.request()
			.get();

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseMsg.getStatus());
		assertNull(responseMsg.readEntity(Album.class));
	}

	@Test
	@DisplayName("Posting an album should work")
	void testPostAlbum(final WebTarget target) {
		final Response responseMsg = target
			.path("api/album/album")
			.request()
			.post(Entity.text(""));

		final Album albumWithID = responseMsg.readEntity(Album.class);

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertEquals(1, albumWithID.getId());
	}

	@Test
	@DisplayName("Crashing the database when posting an album should be OK")
	void testPostAlbumCrashDB(final WebTarget target) {
		// I want to crash the transaction to go into the exception
		// So I create a transaction and I open it so that the next transaction
		// will not be possible and the request will crash
		// The goal here is to check that even on error the service works.
		final EntityTransaction tr = em.getTransaction();
		tr.begin();

		final Response responseMsg = target
			.path("api/album/album")
			.request()
			.post(Entity.text(""));

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseMsg.getStatus());
		assertNull(responseMsg.readEntity(Album.class));
	}

	@Test
	@DisplayName("Crashing the database when posting an album should not prevent next operations")
	void testPostAlbumCrashDBButThenWorks(final WebTarget target) {
		final EntityTransaction tr = em.getTransaction();
		tr.begin();

		target
			.path("api/album/album")
			.request()
			.post(Entity.text(""));

		final Response responseMsg = target
			.path("api/album/album")
			.request()
			.post(Entity.text(""));

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertEquals(1, responseMsg.readEntity(Album.class).getId());
	}


	@Test
	@DisplayName("Getting an album when one is created should return the album")
	void testgetAlbum(final WebTarget target) {
		final var album = target
			.path("api/album/album")
			.request()
			.post(Entity.text(""))
			.readEntity(Album.class);

		final Response responseMsg = target
			.path("api/album/album/" + album.getId())
			.request()
			.get();

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertEquals(album.getId(), responseMsg.readEntity(Album.class).getId());
	}

	@Test
	@DisplayName("Posting a new album when one album already exists should replace this last")
	void testPostANewAlbum(final WebTarget target) {
		target
			.path("api/album/album")
			.request()
			.post(Entity.text(""));

		final Response responseMsg = target
			.path("api/album/album")
			.request()
			.post(Entity.text(""));

		final Album albumWithID = responseMsg.readEntity(Album.class);

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		assertEquals(2, albumWithID.getId());
	}

//	@Test
//	@DisplayName("Adding a player into an album should work")
//	void testAddPlayerIntoAnAlbum(final WebTarget target) {
//		final var album = target
//			.path("api/album")
//			.request()
//			.post(Entity.text(""))
//			.readEntity(Album.class);
//
//		final var player = target
//			.path("api/player")
//			.request()
//			.post(Entity.xml(new Player("Footix")))
//			.readEntity(Player.class);
//
//		final Response responseMsg = target
//			.path("api/album/player/" + album.getId() + "/" + player.getId())
//			.request()
//			.put(Entity.text(""));
//
//		final Album newAlbum = responseMsg.readEntity(Album.class);
//
//		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
//		assertEquals(Set.of(player), newAlbum.getPlayers());
//	}


//	@Test
//	@DisplayName("Posting a player card without an album should not work")
//	public void testPostPlayercardNoAlbum(final WebTarget target) {
//		final Response res = target
//			.path("api/album/playercard")
//			.request()
//			.post(Entity.json(new PlayerCardDTO(1,
//				LocalDateTime.of(2019, 1, 23, 12, 0).format(DateTimeFormatter.ISO_DATE_TIME),"foo.png")));
//
//		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
//	}

//	@Nested
//	class PostPlayerCard {
//		PlayerCardDTO cardDTO;
//
//		@BeforeEach
//		void setUp(final WebTarget target) {
//			target
//				.path("api/album/album")
//				.request()
//				.post(Entity.text(""));
//
//			final Player player = target
//				.path("api/album/player")
//				.request()
//				.post(Entity.json(new Player("Raymond")))
//				.readEntity(Player.class);
//
//			cardDTO = new PlayerCardDTO(player.getId(),
//				LocalDateTime.of(2019, 1, 23, 12, 0).format(DateTimeFormatter.ISO_DATE_TIME),
//				"https://3.bp.blogspot.com/-CvBaKv_U0W4/Tq61vTIs0lI/AAAAAAAATw8/0TLHyZ-jJqQ/s1600/Tony+VAIRELLES+Panini+France+2000.png");
//		}
//
//		@Test
//		@DisplayName("Posting a player card should work")
//		public void testPostPlayercard(final WebTarget target) {
//			final Response res = target
//				.path("api/album/playercard")
//				.request()
//				.post(Entity.json(cardDTO));
//
//			final PlayerCard card = res.readEntity(PlayerCard.class);
//
//			assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
//			assertNotNull(card);
//			assertTrue(card.getId() > 0);
//		}
//
//		@Test
//		@DisplayName("Posting a player card should work")
//		public void testPostPlayercardCrashWithNoPicture(final WebTarget target) {
//			cardDTO.img = null;
//			final Response res = target
//				.path("api/album/playercard")
//				.request()
//				.post(Entity.json(cardDTO));
//
//			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
//			assertNull(res.readEntity(PlayerCard.class));
//		}
//
//		@Test
//		@DisplayName("Crash the database while posting a player card should be supported")
//		public void testPostPlayercardCrashDB(final WebTarget target) {
//			em.getTransaction().begin();
//			final Response res = target
//				.path("api/album/playercard")
//				.request()
//				.post(Entity.json(cardDTO));
//
//			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
//			assertNull(res.readEntity(PlayerCard.class));
//		}
//
//
//		@Test
//		@DisplayName("Crash the database while posting a player card should not block be app")
//		public void testPostPlayercardCrashDBOKAfter(final WebTarget target) {
//			em.getTransaction().begin();
//			target
//				.path("api/album/playercard")
//				.request()
//				.post(Entity.json(cardDTO));
//
//			final Response res = target
//				.path("api/album/playercard")
//				.request()
//				.post(Entity.json(cardDTO));
//
//			final PlayerCard card = res.readEntity(PlayerCard.class);
//
//			assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
//			assertNotNull(card);
//			assertTrue(card.getId() > 0);
//		}
//
//		@Test
//		@DisplayName("Posting a playercard that uses an unkown player should not work")
//		public void testPostPlayercardWithUnkownPlayer(final WebTarget target) {
//			cardDTO.id = 1000;
//
//			final Response res = target
//				.path("api/album/playercard")
//				.request()
//				.post(Entity.json(cardDTO));
//
//
//			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
//			assertNull(res.readEntity(PlayerCard.class));
//		}
//	}
//
//
//
//	@Test
//	@DisplayName("Deleting a player card without any album should be managed")
//	void testDeletePlayerCardNoAlbum(final WebTarget target) {
//		final Response res = target
//			.path("api/album/playercard/1")
//			.request()
//			.delete();
//
//		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
//	}
//
//	@Nested
//	class DeletePlayerCard {
//		PlayerCard card;
//
//		@BeforeEach
//		void setUp(final WebTarget target) {
//			target
//				.path("api/album/album")
//				.request()
//				.post(Entity.text(""));
//
//			final Player player = target
//				.path("api/album/player")
//				.request()
//				.post(Entity.json(new Player("Raymond")))
//				.readEntity(Player.class);
//
//			final PlayerCardDTO cardDTO = new PlayerCardDTO(player.getId(),
//				LocalDateTime.of(2019, 1, 23, 12, 0).format(DateTimeFormatter.ISO_DATE_TIME),
//				"https://3.bp.blogspot.com/-CvBaKv_U0W4/Tq61vTIs0lI/AAAAAAAATw8/0TLHyZ-jJqQ/s1600/Tony+VAIRELLES+Panini+France+2000.png");
//
//			card = target
//				.path("api/album/playercard")
//				.request()
//				.post(Entity.json(cardDTO))
//				.readEntity(PlayerCard.class);
//		}
//
//		@Test
//		@DisplayName("Deleting a player card should work")
//		void testDeletePlayerCard(final WebTarget target) {
//			final Response res = target
//				.path("api/album/playercard/" + card.getId())
//				.request()
//				.delete();
//
//			assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
//		}
//
//		@Test
//		@DisplayName("Deleting a player card without any album should be managed")
//		void testDeletePlayerCardCrash(final WebTarget target) {
//			em.getTransaction().begin();
//			final Response res = target
//				.path("api/album/playercard/" + card.getId())
//				.request()
//				.delete();
//
//			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
//		}
//
//		@Test
//		@DisplayName("Deleting a player card after a database crash shoulb work")
//		void testDeletePlayerCardCrashOkAfter(final WebTarget target) {
//			em.getTransaction().begin();
//			target
//				.path("api/album/playercard/" + card.getId())
//				.request()
//				.delete();
//
//			final Response res = target
//				.path("api/album/playercard/" + card.getId())
//				.request()
//				.delete();
//
//			assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
//		}
//
//		@Test
//		@DisplayName("Deleting a player card with an incorrect ID should be managed")
//		void testDeletePlayerCardNoID(final WebTarget target) {
//			final Response res = target
//				.path("api/album/playercard/" + 100)
//				.request()
//				.delete();
//
//			assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
//		}
//	}
}
