package fr.insarennes.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class CM extends Cours {
	public CM() {
		super();
	}

	public CM(final Matiere m, final LocalDateTime h, final Enseignant e, final Duration d) {
		super(m, h, e, d);
	}
}
