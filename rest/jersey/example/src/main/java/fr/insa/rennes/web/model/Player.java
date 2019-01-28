package fr.insa.rennes.web.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({@NamedQuery(name = "getPlayerWithName", query = "SELECT p FROM Player p WHERE p.name=:name")})
@XmlRootElement
@XmlSeeAlso(BaseballPlayer.class) // Helps JAX-RS during (un-)marchalling operations
// For this class the getters will be used to identify the attribute to marshall, here: getName()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Player extends ModelElement {
	//	@Id
	//	@GeneratedValue
	//	private int id; // The id is provided by the super class

	@Basic(optional = false) // Not mandatory
	@Column(name = "P_NAME", nullable = false) // Optional. Put here in an illustrative purpose.
	protected String name; // Idem

	@ManyToOne
	private Album album;

	// The persistence API (JPA) will ignore this attribute
	@Transient
	protected boolean anAttributeNotToMakePersistent;

	protected Player() {
		super();
	}

	public Player(final String playerName) {
		super();
		name = playerName;
	}

	@XmlAttribute // Optional, if marshalled in XML, will produce an XML attribute, not an XML tag
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = Objects.requireNonNull(name);
	}

	// The marshalling will ignore this property
	@XmlTransient
	public boolean isAnAttributeNotToMakePersistent() {
		return anAttributeNotToMakePersistent;
	}

	public void setAnAttributeNotToMakePersistent(final boolean anAttributeNotToMakePersistent) {
		this.anAttributeNotToMakePersistent = anAttributeNotToMakePersistent;
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("name", name)
			.add("id", id)
			.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Player)) {
			return false;
		}
		final Player player = (Player) o;
		return isAnAttributeNotToMakePersistent() == player.isAnAttributeNotToMakePersistent() && Objects.equals(getName(), player.getName()) && Objects.equals(album, player.album);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), album, isAnAttributeNotToMakePersistent());
	}
}
