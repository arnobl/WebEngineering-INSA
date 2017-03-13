package fr.insa.rennes.web.model;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Player extends ModelElement {
	//	@Id
	//	@GeneratedValue
	//	private int id; // The id is provided by the super class

	@Basic(optional = false) // Not mandatory
	@Column(name = "P_NAME", nullable = false) // Optional. Put here in an illustrative purpose.
	private String name; // Idem

	@Transient
	private boolean anAttributeNotToMakePersistent;

	protected Player() {
		super();
	}

	public Player(final String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = Objects.requireNonNull(name);
	}
}
