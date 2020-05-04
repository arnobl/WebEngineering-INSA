package fr.insarennes.model;

import com.google.common.base.MoreObjects;
import java.time.Duration;
import java.time.LocalDateTime;

public class TD extends Cours {
	TD() {
		super();
	}

	public TD(final Matiere m, final LocalDateTime h, final Enseignant e, final Duration d) {
		super(m, h, e, d);
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("matiere", matiere)
			.add("horaire", horaire)
			.add("ens", ens)
			.add("duration", duration)
			.add("id", id)
			.toString();
	}
}
