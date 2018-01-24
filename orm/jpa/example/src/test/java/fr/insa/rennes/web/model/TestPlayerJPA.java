package fr.insa.rennes.web.model;

import java.util.List;
import javax.persistence.TypedQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
	public void testSelectP1() {
		em.getTransaction().begin();

		final List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.name='P1'", Player.class).getResultList();

		em.getTransaction().commit();

		assertEquals(1, players.size());
		assertEquals(player, players.get(0));
	}

	@Test
	public void testSelectP2() {
		em.getTransaction().begin();

		final List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.name='P2'", Player.class).getResultList();

		em.getTransaction().commit();

		assertEquals(1, players.size());
		assertEquals(p2, players.get(0));
	}

	@Test
	public void testFindPlayer() {
		em.getTransaction().begin();

		final Player foundPlayer = em.find(Player.class, p2.getId());

		em.getTransaction().commit();

		assertEquals(p2, foundPlayer);
	}


	// DO NOT WRITE UNIT TEST LIKE THIS.
	// Demonstration purpose only.
	// Shows that if a crash occurs during a transaction and the transaction is not closed, the app will throw
	// an IllegalStateException for each next transaction.
	@Test(expected = IllegalStateException.class)
	public void testForceCrash() {
		try {
			em.getTransaction().begin();

			final Player p2 = new Player();
			p2.setName(null);

			em.getTransaction().commit();
		}catch(final NullPointerException ex) {
			em.getTransaction().begin();
			final Player foundPlayer = em.find(Player.class, p2.getId());
			em.getTransaction().commit();
		}
	}

	// Following the previous "test", this one -- still, DO NOT WRITE UNIT TEST LIKE THIS -- shows how to rollback on failures
	@Test
	public void testForceCrashRoolback() {
		try {
			em.getTransaction().begin();

			final Player p2 = new Player();
			p2.setName(null);

			em.getTransaction().commit();
		}catch(final NullPointerException ex) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.getTransaction().begin();
			final Player foundPlayer = em.find(Player.class, p2.getId());
			em.getTransaction().commit();
		}
	}


	@Test
	public void testQuerygetPlayerWithName() {
		em.getTransaction().begin();
		final TypedQuery<Player> query = em.createNamedQuery("getPlayerWithName", Player.class);
		em.getTransaction().commit();
		final Player foundPlayer = query.setParameter("name", "P1").getSingleResult();

		assertEquals(player, foundPlayer);
	}
}
