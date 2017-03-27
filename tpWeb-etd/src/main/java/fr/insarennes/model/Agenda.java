package fr.insarennes.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//TODO Q4
//http://www.oracle.com/technetwork/middleware/ias/toplink-jpa-annotations-096251.html
public class Agenda extends CalendarElement {
	private String name;
	private Set<Cours> cours;

	/**
	 * Creates the agenda with a name and an empty set of courses, teachers, and topics.
	 */
	public Agenda() {
		super();
		name = "myAgenda";
		cours = new HashSet<>();
	}

	/**
	 * @return The name of the calendar.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the calendar.
	 * @param name The new name. Nothing is done is null.
	 */
	public void setName(final String name) {
		if(name != null) {
			this.name = name;
		}
	}

	/**
	 * Adds a course to the calendar.
	 * @param c The course to add. Nothing is done if null.
	 */
	public void addCours(final Cours c) {
		if(c != null) {
			cours.add(c);
			c.setAgenda(this);
		}
	}

	public Set<Cours> getCours() {
		return cours;
	}

	public void setCours(final Set<Cours> cs) {
		cours = Objects.requireNonNull(cs);
	}

	@Override
	public String toString() {
		return "Agenda{" + "id=" + getId() + "cours=" + cours + ", name='" + name + '\'' + '}';
	}
}
