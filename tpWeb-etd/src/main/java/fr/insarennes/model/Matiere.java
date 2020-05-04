package fr.insarennes.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public class Matiere extends CalendarElement {
	private String name;
	private int annee;

	Matiere() {
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
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("annee", annee).add("id", id).toString();
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

//	@Override
//	public boolean equals(final Object o) {
//		if(this == o) {
//			return true;
//		}
//		if(!(o instanceof Matiere)) {
//			return false;
//		}
//		if(!super.equals(o)) {
//			return false;
//		}
//		final Matiere matiere = (Matiere) o;
//		return getAnnee() == matiere.getAnnee() && Objects.equals(getName(), matiere.getName());
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(super.hashCode(), getName(), getAnnee());
//	}
}
