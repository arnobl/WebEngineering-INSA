package fr.insarennes.model;

import java.util.Objects;

public class Enseignant extends CalendarElement {
	private String name;

	public Enseignant() {
		this("enseignant");
	}

	public Enseignant(final String n) {
		super();
		name = Objects.requireNonNull(n);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}

		final Enseignant ens = (Enseignant) o;

		return name.equals(ens.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "Enseignant{id=" + getId() + ", name='" + name + '\'' + '}';
	}
}
