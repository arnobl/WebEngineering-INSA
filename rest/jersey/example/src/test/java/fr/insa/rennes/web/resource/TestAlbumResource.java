package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Picture;
import fr.insa.rennes.web.model.Player;
import fr.insa.rennes.web.model.PlayerCard;
import fr.insa.rennes.web.utils.MyExceptionMapper;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class TestAlbumResource extends JerseyTest {
	// @Inject permits to get the AlbumResource singleton created by Jersey.
	@Inject private AlbumResource albumResource;

    @Override
    protected Application configure() {
		// For the logger
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.WARN);

        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        // You must register the service you want to test
        // register(this) is just used to allow this class to access the CalendarResource instance.
        return new ResourceConfig(AlbumResource.class).register(this).register(MyExceptionMapper.class).
			property("jersey.config.server.tracing.type", "ALL");
    }

	@Override
	@After // The @After annotation permits to tag methods to be executed after each test. This method is usually called tearDown.
	public void tearDown() throws Exception {
		super.tearDown();
		// It is necessary to flush the database between each test to avoid side-effects.
		albumResource.flush();
	}

	@Test
    public void testPostPlayerOK() {
        // Creation of a player.
		Player player = new Player("Raymond");
        // Asks the addition of the player object to the server.
        // target(...) is provided by the JerseyTest class to ease the writing of the tests
        // the URI "album/player" first identifies the resource class ("album") to which the request will be sent.
        // "player" permits the identification of the resource method that will process the request.
        // post(...) corresponds to the HTTP verb POST.
        // To POST an object to the server, this object must be serialised into a standard format: XML and JSON
        // Jersey provides operations (Entity.xml(...)) and processes to automatically serialised objects.
        // To do so (for both XML and Json), the object's class must be tagged with the annotation @XmlRootElement.
        // A Response object is returned by the server.
        Response responseMsg = target("album/player").request().post(Entity.xml(player));
        // This Response object provides a status that can be checked (see the HTTP header status picture in the subject).
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        // The Response object may also embed an object that can be read (give the expected class as parameter).
		Player playerWithID = responseMsg.readEntity(Player.class);
        // The two Players instances must be equals (but their ID that are not compared in the equals method).
        assertEquals(player, playerWithID);
        // But their ID will differ since the instance returned by the server has been serialised in the database and thus
        // received a unique ID (see the JPA practice session).
        assertNotSame(player.getId(), playerWithID.getId());
    }

    @Test
	public void testGetPlayerOK() {
		Player player = target("album/player").request().post(Entity.xml(new Player("Raymond"))).readEntity(Player.class);
		Response responseAfterPost = target("album/player/"+player.getName()).request().get();
		// This awful code is required to get a list.
		List<Player> players = responseAfterPost.readEntity(new GenericType<List<Player>>(){});

		assertEquals(1, players.size());
		assertEquals(player, players.get(0));
	}

    @Test
    public void testChangePlayerNameOK() {
		Player player = target("album/player").request().post(Entity.xml(new Player("Raymond"))).readEntity(Player.class);
        Player playerWithNewName = target("album/player/"+player.getId()+"/Robert").request().put(Entity.text("")).readEntity(Player.class);
        assertEquals("Robert", playerWithNewName.getName());
        assertEquals(player.getId(), playerWithNewName.getId());
    }

    @Test
    public void testDeletePlayerOK() {
		Player player = target("album/player").request().post(Entity.xml(new Player("Raymond"))).readEntity(Player.class);

        Response responseAfterPost = target("album/player/"+player.getId()).request().delete();
        Response responseAfterDeletion = target("album/player/"+player.getName()).request().get();
		List<Player> players = responseAfterDeletion.readEntity(new GenericType<List<Player>>(){});

        assertEquals(Response.Status.OK.getStatusCode(), responseAfterPost.getStatus());
        assertTrue(players.isEmpty());
    }

	@Test
	public void testPostPlayercard() {
		final Player player = target("album/player").request().post(Entity.xml(new Player("Raymond"))).readEntity(Player.class);
		final Response res = target("album/playercard").request().post(Entity.xml(new PlayerCard(player, new Picture("pic/gernot.jpg"),
			LocalDate.of(2015, Month.JANUARY, 23))));
		final PlayerCard card = res.readEntity(PlayerCard.class);
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
		assertNotNull(card);
	}
}
