package fr.insa.rennes.web.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.glassfish.hk2.api.Factory;

public class DBFactory implements Factory<EntityManager> {
	@Override
	public EntityManager provide() {
		final EntityManagerFactory db = Persistence.createEntityManagerFactory("playerCardDB");
		return db.createEntityManager();
	}

	@Override
	public void dispose(final EntityManager entityManager) {
		entityManager.getEntityManagerFactory().close();
		entityManager.close();
	}
}
