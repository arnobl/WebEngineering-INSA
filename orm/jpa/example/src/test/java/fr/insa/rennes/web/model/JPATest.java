package fr.insa.rennes.web.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

abstract class JPATest {
	EntityManagerFactory emf;
	EntityManager em;

	@BeforeEach
	void setUp() {
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


	@AfterEach
	void tearDown() {
		em.clear();
		em.close();
		emf.close();
	}
}
