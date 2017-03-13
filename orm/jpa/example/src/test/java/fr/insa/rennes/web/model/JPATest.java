package fr.insa.rennes.web.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;

abstract class JPATest {
	EntityManagerFactory emf;
	EntityManager em;
	EntityTransaction tr;

	@Before
	public void setUp() throws Exception {
		// "playerCardDB" is the name of the persistence unit as defined in persistence.xml
		emf = Persistence.createEntityManagerFactory("playerCardDB");
		em = emf.createEntityManager();
		tr = em.getTransaction();

		fillDatabase();
	}


	void fillDatabase() {

	}


	@After
	public void tearDown() throws Exception {
		em.clear();
		em.close();
		emf.close();
	}
}
