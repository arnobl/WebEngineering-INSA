package fr.insa.rennes.web.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ModelElement {
	@Id
	@GeneratedValue
	protected int id;

	protected ModelElement() {
		super();
	}

	public int getId() {
		return id;
	}

	protected void setId(final int id) {
		this.id = id;
	}
}
