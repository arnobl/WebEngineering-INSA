package fr.insa.rennes.web.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

abstract class JPATest {
	EntityManagerFactory emf;
	EntityManager em;

	@Before
	public void setUp() {
		// For the logger
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.WARN);

		// "playerCardDB" is the name of the persistence unit as defined in persistence.xml
		emf = Persistence.createEntityManagerFactory("playerCardDB");
		em = emf.createEntityManager();

		fillDatabase();
	}


	void fillDatabase() {
	}


	@After
	public void tearDown() {
		em.clear();
		em.close();
		emf.close();
	}
}
