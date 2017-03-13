package fr.insa.rennes.web.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Album {
	@Id
	@GeneratedValue
	private int id; // Should factorise this code in a super abstract class. See: the with inheritance example

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

	public void setCards(final Set<PlayerCard> cards) {
		this.cards = cards;
	}

	public void addCard(final PlayerCard pc) {
		cards.add(pc);
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
