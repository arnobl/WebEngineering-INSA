package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Album;
import fr.insa.rennes.web.model.BaseballPlayer;
import fr.insa.rennes.web.model.ModelElement;
import fr.insa.rennes.web.model.Picture;
import fr.insa.rennes.web.model.Player;
import fr.insa.rennes.web.model.PlayerCard;
import fr.insa.rennes.web.model.Position;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarshalling {
	EntityManagerFactory emf;
	EntityManager em;
	JAXBContext ctx;

	@BeforeAll
	static void setUpJSONFactory() {
		System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
	}

	@BeforeEach
	void setUp() throws JAXBException {
		emf = Persistence.createEntityManagerFactory("playerCardDB");
		em = emf.createEntityManager();
		ctx = JAXBContext.newInstance(Album.class, Data.class);
	}

	@Test
	void testMarshallPlayer() throws IOException, JAXBException {
		final Player player = new Player("Raymond");
		em.persist(player);
		assertMarchall(player);
	}

	@Test
	void testMarshallBaseballPlayer() throws IOException, JAXBException {
		final BaseballPlayer bplayer = new BaseballPlayer("Duke", Position.CATCHER, 10);
		em.persist(bplayer);
		assertMarchall(bplayer);
	}

	@Test
	void testMarshallPlayerCard() throws IOException, JAXBException {
		final Player player = new Player("Raymond");
		final Picture picture = new Picture("/my/path");
		final PlayerCard pc = new PlayerCard(player, picture, null, LocalDateTime.now());
		em.persist(pc);
		assertMarchall(player, pc);
	}

	@Test
	void testMarshallAlbum() throws IOException, JAXBException {
		final Player player = new Player("Raymond");
		final BaseballPlayer bplayer = new BaseballPlayer("Duke", Position.CATCHER, 10);
		final PlayerCard pc = new PlayerCard(player, new Picture("/my/path1"), null, LocalDateTime.now());
		final PlayerCard pc2 = new PlayerCard(bplayer, new Picture("/my/path2"), null, LocalDateTime.now());
		final Album album = new Album();
		album.addCard(pc);
		album.addCard(pc2);
		em.persist(album);
		assertMarchall(album);
	}

	void assertMarchall(final ModelElement... elt) throws IOException, JAXBException {
		final Marshaller marshaller = ctx.createMarshaller();
		final Unmarshaller unmarshaller = ctx.createUnmarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// If want to produce json:
//		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
//		unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");

		try(final PipedOutputStream out = new PipedOutputStream();
			final InputStream in = new PipedInputStream(out)) {

			final Data<Object> dataToMarshall = new Data<>(elt);

			marshaller.marshal(dataToMarshall, System.out);
			marshaller.marshal(dataToMarshall, out);
			out.close();

			final Object unmarshal = unmarshaller.unmarshal(in);
			assertEquals(dataToMarshall, unmarshal);
		}
	}
}
