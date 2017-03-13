package fr.insa.rennes.web.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAlbumJPA extends JPATest {
	Album album;
	Player p1;
	PlayerCard pc1;

	@Override
	void fillDatabase() {
		album = new Album();
		em.persist(album);

		Picture pic1 = new Picture("pic/gernot.jpg");

		p1 = new Player("P1");
		em.persist(p1);

		pc1 = new PlayerCard(p1, pic1, LocalDate.of(2015, Month.JANUARY, 23));
		pc1.setAlbum(album);
		em.persist(pc1);

		album.addCard(pc1);
	}


	@Test
	public void testGetAlbum() throws Exception {
		tr.begin();

		List<Album> players = em.createQuery("SELECT a FROM Album a", Album.class).getResultList();

		tr.commit();

		assertEquals(1, players.size());
		assertEquals(album, players.get(0));
	}


	@Test
	public void testAlbumWithOnePlayerCard() throws Exception {
		tr.begin();

		List<Album> players = em.createQuery("SELECT a FROM Album a", Album.class).getResultList();

		tr.commit();

		assertEquals(1, players.get(0).getCards().size());
		assertEquals(pc1, players.get(0).getCards().iterator().next());
	}
}
