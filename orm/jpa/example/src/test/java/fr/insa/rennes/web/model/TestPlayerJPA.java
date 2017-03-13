package fr.insa.rennes.web.model;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayerJPA extends JPATest {
	Player player;


	@Override
	void fillDatabase() {
		player = new Player("P1");
		em.persist(player);
	}

	@Test
	public void testSelectP1() {
		tr.begin();

		List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.name='P1'",  Player.class).getResultList();

		tr.commit();

		assertEquals(1, players.size());
		assertEquals(player, players.get(0));
	}
}
