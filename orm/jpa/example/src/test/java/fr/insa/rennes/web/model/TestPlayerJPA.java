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
		em.persist(player);

		p2 = new BaseballPlayer("P2", 10);
		em.persist(p2);
	}

	@Test
	public void testSelectP1() {
		tr.begin();

		List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.name='P1'",  Player.class).getResultList();

		tr.commit();

		assertEquals(1, players.size());
		assertEquals(player, players.get(0));
	}

	@Test
	public void testSelectP2() {
		tr.begin();

		List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.name='P2'",  Player.class).getResultList();

		tr.commit();

		assertEquals(1, players.size());
		assertEquals(p2, players.get(0));
	}

	@Test
	public void testFindPlayer() {
		tr.begin();

		Player foundPlayer = em.find(Player.class, p2.getId());

		tr.commit();

		assertEquals(foundPlayer, p2);
	}


	// Do not write unit test like this.
	// Demonstration purpose only.
	// Shows that if a crash occurs during a transaction and the transaction is not closed, the app will throw
	// an IllegalStateException for each next transaction.
	@Test(expected = IllegalStateException.class)
	public void testForceCrash() {
		try {
			tr.begin();

			Player p2 = new Player();
			p2.setName(null);

			tr.commit();
		}catch(NullPointerException ex) {
			tr.begin();
			Player foundPlayer = em.find(Player.class, p2.getId());
			tr.commit();
		}
	}

	// Following the previous "test", this one -- still, do not write tests like this -- shows how to rollback on failures
	@Test
	public void testForceCrashRoolback() {
		try {
			tr.begin();

			Player p2 = new Player();
			p2.setName(null);

			tr.commit();
		}catch(NullPointerException ex) {
			tr.rollback();
			tr.begin();
			Player foundPlayer = em.find(Player.class, p2.getId());
			tr.commit();
		}
	}


	@Test
	public void testQuerygetPlayerWithName() {
		tr.begin();
		TypedQuery<Player> query = em.createNamedQuery("getPlayerWithName", Player.class);
		tr.commit();
		Player foundPlayer = query.setParameter("name", "P1").getSingleResult();

		assertEquals(player, foundPlayer);
	}
}
