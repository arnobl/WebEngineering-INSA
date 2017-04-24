package fr.insa.rennes.web.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Album extends ModelElement {
	//	@Id
	//	@GeneratedValue
	//	private int id; // The id is provided by the super class

	@OneToMany(mappedBy = "album", // The back reference to the album in the PlayerCard class. Note that the use of a string is not very maintainable.
		cascade = CascadeType.PERSIST, // This means that if an Album is persisted, all its cards will be also persisted.
		fetch = FetchType.LAZY) // Cards are loaded on demand only.
	private Set<PlayerCard> cards;

	public Album() {
		super();
		cards = new HashSet<>();
	}

	public Set<PlayerCard> getCards() {
		return cards;
	}

	protected void setCards(final Set<PlayerCard> cards) {
		this.cards = cards;
	}

	public void addCard(final PlayerCard pc) {
		cards.add(pc);
	}

	@Override
	public String toString() {
		return "Album{" + "id=" + id + ", cards=" + cards + '}';
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) return true;
		if(!(o instanceof Album)) return false;

		Album album = (Album) o;

		return cards.equals(album.cards);
	}

	@Override
	public int hashCode() {
		return cards.hashCode();
	}
}
