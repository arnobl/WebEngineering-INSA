package fr.insa.rennes.web.model;

import com.google.common.base.MoreObjects;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Album extends ModelElement {
	// The id is provided by the super class

	@OneToMany(mappedBy = "album", // The back reference to the album in the PlayerCard class. Note that the use of a string is not very maintainable.
		cascade = CascadeType.ALL, // This means that if an Album is persisted, all its cards will be also persisted.
		fetch = FetchType.LAZY) // Cards are loaded on demand only.
	private final Set<PlayerCard> cards;

	// We assume that a player is part of an album (cannot be shared, composition)
	@OneToMany(mappedBy = "album",
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY)
	private final Set<Player> players;


	public Album() {
		super();
		cards = new HashSet<>();
		players = new HashSet<>();
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public Set<PlayerCard> getCards() {
		return cards;
	}

	public void addCard(final PlayerCard pc) {
		cards.add(pc);
		if(!players.contains(pc.getPlayer())) {
			addPlayer(pc.getPlayer());
		}
	}

	public void removeCard(final PlayerCard pc) {
		cards.remove(pc);
	}

	public void addPlayer(final Player p) {
		players.add(p);
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("id", id)
			.add("cards", cards)
			.add("players", players)
			.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Album)) {
			return false;
		}
		final Album album = (Album) o;
		return Objects.equals(getCards(), album.getCards()) && Objects.equals(getPlayers(), album.getPlayers());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCards(), getPlayers());
	}
}
