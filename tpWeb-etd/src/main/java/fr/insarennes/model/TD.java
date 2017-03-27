package fr.insarennes.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TD extends Cours {
	public TD() {
		super();
	}

	public TD(final Matiere m, final LocalDateTime h, final Enseignant e, final Duration d) {
		super(m, h, e, d);
	}
}
