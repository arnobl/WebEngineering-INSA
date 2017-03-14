package fr.insa.rennes.web.resource;

import fr.insa.rennes.web.model.Album;
import io.swagger.annotations.Api;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Path;

@Singleton
@Path("album")
@Api(value="/album" , description = "Operations on the album")
public class AlbumResource {
	private final EntityManagerFactory emf;
	private final EntityManager em;
	private final Album album;


	public AlbumResource() {
		super();
		album = new Album();
		emf = Persistence.createEntityManagerFactory("playerCardDB");
		em = emf.createEntityManager();
	}

	protected void flush() {
		em.clear();
		em.close();
		emf.close();
	}

	@Override
	protected void finalize() throws Throwable {
		flush();
		super.finalize();
	}
}
