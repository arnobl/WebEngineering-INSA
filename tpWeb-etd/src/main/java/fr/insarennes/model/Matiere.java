package fr.insarennes.model;

import java.util.Objects;

public class Matiere extends CalendarElement {
	private String name;

	private int annee;

	public Matiere() {
		super();
		name = "matiere";
		annee = -1;
	}

	public Matiere(final String n, final int a) {
		super();
		name = Objects.requireNonNull(n);
		annee = a;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Matiere matiere = (Matiere) o;
		return annee == matiere.annee && name.equals(matiere.name);

	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + annee;
		return result;
	}

	@Override
	public String toString() {
		return "Matiere{id=" + getId() + ", name='" + name + '\'' + ", annee=" + annee + '}';
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(final int annee) {
		this.annee = annee;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
