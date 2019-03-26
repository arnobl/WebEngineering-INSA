package fr.insarennes;

import fr.insarennes.model.Agenda;
import fr.insarennes.model.Enseignant;
import fr.insarennes.model.Matiere;
import fr.insarennes.model.TD;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Table;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestJPACalendar {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tr;

	private Agenda agenda;
	private Enseignant blouin;
	private Enseignant leplumey;
	private Enseignant bertier;
	private Matiere web;
	private Matiere mobile;
	private TD td1;
	private TD td2;
	private TD td3;


	@BeforeEach
	void setUp() {
		BasicConfigurator.configure();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.WARN);

		emf = Persistence.createEntityManagerFactory("agendapp");
		em = emf.createEntityManager();
		tr = em.getTransaction();

		createTable();
	}

	private void createTable() {
		agenda = new Agenda();
		agenda.setName("agenda1");
		em.getTransaction().begin();
		em.persist(agenda);

		blouin = new Enseignant("Blouin");
		leplumey = new Enseignant("Leplumey");
		bertier = new Enseignant("Bertier");
		em.persist(blouin);
		em.persist(leplumey);
		em.persist(bertier);

		web = new Matiere("Web", 3);
		mobile = new Matiere("Prog Mobile", 3);
		em.persist(web);
		em.persist(mobile);

		LocalDateTime h1 = LocalDate.of(2015, Month.JANUARY, 2).atTime(8, 0);
		LocalDateTime h2 = LocalDate.of(2015, Month.JANUARY, 2).atTime(9, 0);
		LocalDateTime h3 = LocalDate.of(2015, Month.JANUARY, 3).atTime(8, 0);

		td2 = new TD(web, h1, blouin, Duration.ofHours(2L));
		td3 = new TD(web, h3, blouin, Duration.ofHours(2L));
		TD td4 = new TD(mobile, h2, blouin, Duration.ofHours(2L));
		td1 = new TD( web, h2, leplumey, Duration.ofHours(1L));
		agenda.addCours(td1);
		agenda.addCours(td2);
		agenda.addCours(td3);
		em.persist(td2);
		em.persist(td3);
		em.persist(td4);
		em.persist(td1);
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

	@AfterEach
	public void tearDown() {
		em.clear();
		em.close();
		emf.close();
	}

	@Test
	public void testSelectAgenda() {
		tr.begin();

		//        CriteriaQuery<Agenda> c = cb.createQuery(Agenda.class);
		//        List<Agenda> cc = em.createQuery(c.select(c.from(Agenda.class))).getResultList();

		List<Agenda> cc = em.createQuery("SELECT a FROM Agenda a", Agenda.class).getResultList();
		tr.commit();

		assertEquals(1, cc.size());
		Agenda a = cc.get(0);
		assertNotNull(a);
		assertEquals("agenda1", a.getName());
		assertEquals(agenda.getId(), a.getId());
	}


//	@Test
//	public void testCorrectNumberOfRoomsAdded() {
//		tr.begin();
//		//        CriteriaQuery<Salle> c = cb.createQuery(Salle.class);
//		//        List<Salle> cc = em.createQuery(c.select(c.from(Salle.class))).getResultList();
//		List<Salle> cc = em.createQuery("SELECT a FROM Salle a",  Salle.class).getResultList();
//		tr.commit();
//
//		assertEquals(1, cc.size());
//	}
//
//
//	@Test
//	public void testSelectSalleINF16() {
//		tr.begin();
//		//        CriteriaQuery<Salle> c = cb.createQuery(Salle.class);
//		//        Root<Salle> r = c.from(Salle.class);
//		//        List<Salle> cc = em.createQuery(c.select(r).where(cb.equal(r.get("name"), "INFO16"))).getResultList();
//
//		List<Salle> cc = em.createQuery("SELECT a FROM Salle a WHERE a.name='INFO16'",  Salle.class).getResultList();
//		tr.commit();
//
//		assertEquals(1, cc.size());
//		assertEquals(inf16, cc.get(0));
//	}

	@Test
	public void testCorrectNumberOfTeachersAdded() {
		tr.begin();
		//        CriteriaQuery<Enseignant> c = cb.createQuery(Enseignant.class);
		//        List<Enseignant> cc = em.createQuery(c.select(c.from(Enseignant.class))).getResultList();

		List<Enseignant> cc = em.createQuery("SELECT a FROM Enseignant a", Enseignant.class).getResultList();
		tr.commit();

		assertEquals(3, cc.size());
	}

	@Test
	public void testSelectEnseignantBlouin() {
		tr.begin();
		//        CriteriaQuery<Enseignant> c = cb.createQuery(Enseignant.class);
		//        Root<Enseignant> r = c.from(Enseignant.class);
		//        List<Enseignant> cc = em.createQuery(c.select(r).where(cb.equal(r.get("name"), "Blouin"))).getResultList();

		List<Enseignant> cc = em.createQuery("SELECT e FROM Enseignant e WHERE e.name='Blouin'", Enseignant.class).getResultList();
		tr.commit();

		assertEquals(1, cc.size());
		assertEquals(blouin, cc.get(0));
	}

//	@Test
//	public void testCorrectNumberOfGroupsAdded() {
//		tr.begin();
//		//        CriteriaQuery<Groupe> c = cb.createQuery(Groupe.class);
//		//        List<Groupe> cc = em.createQuery(c.select(c.from(Groupe.class))).getResultList();
//
//		List<Groupe> cc = em.createQuery("SELECT g FROM Groupe g", Groupe.class).getResultList();
//		tr.commit();
//
//		assertEquals(1, cc.size());
//	}

//	@Test
//	public void testSelectGroupe21() {
//		tr.begin();
//		//        CriteriaQuery<Groupe> c = cb.createQuery(Groupe.class);
//		//        Root<Groupe> r = c.from(Groupe.class);
//		//        List<Groupe> cc = em.createQuery(c.select(r).where(cb.equal(r.get("name"), "G1.1"))).getResultList();
//
//		List<Groupe> cc = em.createQuery("SELECT g FROM Groupe g WHERE g.name='G1.1'", Groupe.class).getResultList();
//		tr.commit();
//
//		assertEquals(1, cc.size());
//		assertEquals(g11, cc.get(0));
//	}

	@Test
	public void testSelectCoursBlouin() {
		tr.begin();
		//Explicit join notation:
		List<TD> cc = em.createQuery("SELECT c FROM TD c INNER JOIN Enseignant e on c.ens=e AND e.name='Blouin'", TD.class).getResultList();
		// implicit join notation, deprecated in 1992, and its use is not considered a best practice:
		//        List<TP> cc = em.createQuery("SELECT c FROM TP c, Enseignant e where c.ens=e AND e.name='Blouin'", TP.class).getResultList();
		tr.commit();

		System.out.println(cc);
		assertEquals(3, cc.size());
	}

	@Test
	public void testSelectTPWebBlouin() {
		tr.begin();
		List<TD> cc = em.createQuery("SELECT c FROM TD c INNER JOIN Enseignant e ON c.ens=e INNER JOIN Matiere m on c.matiere=m AND e.name='Blouin' AND m.name='Web'", TD.class).getResultList();
		tr.commit();

		assertEquals(2, cc.size());
		assertThat(cc.get(0), anyOf(is(equalTo(td2)), is(equalTo(td3))));
		assertThat(cc.get(1), anyOf(is(equalTo(td2)), is(equalTo(td3))));
	}


//	@Test
//	public void testNamedQuerySalles() {
//		tr.begin();
//		Query querySalles = em.createNamedQuery("Salle.getSalles");
//		tr.commit();
//		List<Salle> salles = querySalles.getResultList();
//		System.out.println(salles);
//	}

//	@Test
//	public void testNamedQuerySallesID() {
//		tr.begin();
//		Query querySalles = em.createNamedQuery("Salle.getSalleID").setParameter("id", 2);
//		tr.commit();
//		assertNotNull(querySalles.getSingleResult());
//	}
}
