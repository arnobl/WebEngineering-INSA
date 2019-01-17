package fr.insa.rennes.web.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAlbumJPA extends JPATest {
	Album album;
	Player p1;
	PlayerCard pc1;

	@Override
	void fillDatabase() {
		album = new Album();
		em.getTransaction().begin();
		em.persist(album);
		em.getTransaction().commit();

		final Picture pic1 = new Picture("pic/gernot.jpg");

		p1 = new Player("P1");
		em.getTransaction().begin();
		em.persist(p1);
		em.getTransaction().commit();

		pc1 = new PlayerCard(p1, pic1, LocalDate.of(2015, Month.JANUARY, 23));
		pc1.setAlbum(album);
		em.getTransaction().begin();
		em.persist(pc1);
		em.getTransaction().commit();

		album.addCard(pc1);
	}


	@Test
	void testGetAlbum() {
		em.getTransaction().begin();

		final List<Album> players = em.createQuery("SELECT a FROM Album a", Album.class).getResultList();

		em.getTransaction().commit();

		assertEquals(1, players.size());
		assertEquals(album, players.get(0));
	}


	@Test
	void testAlbumWithOnePlayerCard() {
		em.getTransaction().begin();

		final List<Album> players = em.createQuery("SELECT a FROM Album a", Album.class).getResultList();

		em.getTransaction().commit();

		assertEquals(1, players.get(0).getCards().size());
		assertEquals(pc1, players.get(0).getCards().iterator().next());
	}
}
