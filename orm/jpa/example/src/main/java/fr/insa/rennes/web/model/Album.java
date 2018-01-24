package fr.insa.rennes.web.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Album extends ModelElement {
	//	@Id
	//	@GeneratedValue
	//	private int id; // The id is provided by the super class

	@OneToMany(mappedBy = "album", // The back reference to the album in the PlayerCard class. Note that the use of a string is not very maintainable.
		cascade = CascadeType.PERSIST, // This means that if an Album is persisted, all its cards will be also persisted.
		fetch = FetchType.LAZY) // Cards are loaded on demand only.
	private final Set<PlayerCard> cards;

	public Album() {
		super();
		cards = new HashSet<>();
	}

	public Set<PlayerCard> getCards() {
		return cards;
	}

	public void addCard(final PlayerCard pc) {
		cards.add(pc);
	}
}
