package fr.insa.rennes.web.model;

import java.util.List;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayerJPA extends JPATest {
	Player player;
	BaseballPlayer p2;


	@Override
	void fillDatabase() {
		player = new Player("P1");
		em.getTransaction().begin();
		em.persist(player);
		em.getTransaction().commit();

		p2 = new BaseballPlayer("P2", Position.CATCHER, 10);
		em.getTransaction().begin();
		em.persist(p2);
		em.getTransaction().commit();
	}

	@Test
	void testSelectP1() {
		em.getTransaction().begin();

		final List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.name='P1'", Player.class).getResultList();

		em.getTransaction().commit();

		assertEquals(1, players.size());
		assertEquals(player, players.get(0));
	}

	@Test
	void testSelectP2() {
		em.getTransaction().begin();

		final List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.name='P2'", Player.class).getResultList();

		em.getTransaction().commit();

		assertEquals(1, players.size());
		assertEquals(p2, players.get(0));
	}

	@Test
	void testFindPlayer() {
		em.getTransaction().begin();

		final Player foundPlayer = em.find(Player.class, p2.getId());

		em.getTransaction().commit();

		assertEquals(p2, foundPlayer);
	}


	@Test
	void testQuerygetPlayerWithName() {
		em.getTransaction().begin();
		final TypedQuery<Player> query = em.createNamedQuery("getPlayerWithName", Player.class);
		em.getTransaction().commit();
		final Player foundPlayer = query.setParameter("name", "P1").getSingleResult();

		assertEquals(player, foundPlayer);
	}
}
