package fr.insarennes.model;

import com.google.common.base.MoreObjects;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.xml.bind.annotation.XmlTransient;

public abstract class Cours extends CalendarElement {
	protected Matiere matiere;
	protected LocalDateTime horaire;
	protected Enseignant ens;
	protected Duration duration;
	protected Agenda agenda;

	public Cours() {
		super();
	}

	public Cours(final Matiere m, final LocalDateTime h, final Enseignant e, final Duration d) {
		super();
		matiere = Objects.requireNonNull(m);
		horaire = Objects.requireNonNull(h);
		ens = Objects.requireNonNull(e);
		duration = Objects.requireNonNull(d);
	}

	public boolean matchesID(final int i) {
		return i == getId() || i == matiere.getId() || i == ens.getId();
	}

	@XmlTransient // The agenda of a cours must not be considered during a REST serialisation.
	public Agenda getAgenda() {
		return agenda;
	}

	protected void setAgenda(final Agenda agenda) {
		this.agenda = agenda;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(final Matiere m) {
		matiere = m;
	}

	public LocalDateTime getHoraire() {
		return horaire;
	}

	public void setHoraire(final LocalDateTime h) {
		horaire = h;
	}

	public Enseignant getEns() {
		return ens;
	}

	public void setEns(final Enseignant e) {
		ens = e;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(final Duration d) {
		duration = d;
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("matiere", matiere)
			.add("horaire", horaire)
			.add("ens", ens)
			.add("duration", duration)
			.add("agenda", agenda)
			.add("id", id)
			.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Cours)) {
			return false;
		}
		if(!super.equals(o)) {
			return false;
		}
		final Cours cours = (Cours) o;
		return Objects.equals(getMatiere(), cours.getMatiere()) && Objects.equals(getHoraire(), cours.getHoraire()) && Objects.equals(getEns(),
			cours.getEns()) && Objects.equals(getDuration(), cours.getDuration()) && Objects.equals(getAgenda(), cours.getAgenda());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getMatiere(), getHoraire(), getEns(), getDuration(), getAgenda());
	}
}
