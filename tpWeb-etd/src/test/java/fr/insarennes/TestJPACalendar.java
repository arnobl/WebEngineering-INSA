package fr.insarennes;

import fr.insarennes.model.Agenda;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJPACalendar {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tr;

	private Agenda agenda;


	@Before
	public void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("agendapp");
		em = emf.createEntityManager();
		tr = em.getTransaction();

		createTable();
	}

	private void createTable() throws SQLException {
		agenda = new Agenda();
		agenda.setName("agenda1");
		em.getTransaction().begin();
		em.persist(agenda);
//        Use em.persist to put an object into the database
//        em.persist(myObjectToPutIntoTheDatabase);

		em.getTransaction().commit();
		printTables();
	}


	public void printTables() {
		System.out.println(em.getMetamodel().getEntities().stream().map(e -> {
			Table t = e.getJavaType().getAnnotation(Table.class);
			return (t == null ? e.getName() : t.name()) + (e.getSupertype() == null ? "" : " -> " +
				e.getSupertype().getJavaType().getSimpleName()) + e.getAttributes().stream().map(a -> a.getName() + ":" +
				a.getJavaType().getSimpleName()).collect(Collectors.joining(", ", "[", "]"));
		}).collect(Collectors.joining("\n", "****************\nTables:\n", "\n****************")));
	}

	@After
	public void tearDown() throws Exception {
		em.clear();
		em.close();
		emf.close();
	}

	@Test
	public void testSelectAgenda() {
		tr.begin();
		List<Agenda> cc = em.createQuery("SELECT a FROM AGENDA a", Agenda.class).getResultList();
		tr.commit();

//        assertEquals(1, cc.size());
//        Agenda a = cc.get(0);
//        assertNotNull(a);
//        assertEquals("agenda1", a.getName());
//        assertEquals(agenda.getId(), a.getId());
	}


}
