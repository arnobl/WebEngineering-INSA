package fr.insa.rennes.web.model;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(
		name="getPlayerWithName",
		query="SELECT p FROM Player p WHERE p.name=:name"
	),
})
@XmlRootElement
public class Player extends ModelElement {
	//	@Id
	//	@GeneratedValue
	//	private int id; // The id is provided by the super class

	@Basic(optional = false) // Not mandatory
	@Column(name = "P_NAME", nullable = false) // Optional. Put here in an illustrative purpose.
	protected String name; // Idem

	@Transient
	protected boolean anAttributeNotToMakePersistent;

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

	@Override
	public String toString() {
		return "Player{" + "id=" + id + ", name='" + name + '\'' + '}';
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) return true;
		if(!(o instanceof Player)) return false;

		Player player = (Player) o;

		return name.equals(player.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
