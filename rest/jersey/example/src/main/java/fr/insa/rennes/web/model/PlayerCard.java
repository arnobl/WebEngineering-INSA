package fr.insa.rennes.web.model;

import com.google.common.base.MoreObjects;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlRootElement
// For this class the attributes will be used to identify what to marshall
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerCard extends ModelElement {
	// The id is provided by the super class

	// Note that the @XmlAttribute and @XmlElement annotations are not
	// mandatory here since the marshalling strategy grabs all the fields
	// (@XmlAccessorType(XmlAccessType.FIELD))

	@ManyToOne
	private Album album;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PC_PLAYER", nullable = false)
	@XmlIDREF
	@XmlAttribute
	private Player player;

	@XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
	@XmlAttribute
	private LocalDateTime date;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="pic", column=@Column(name="FRONT_PIC", nullable = false)),
	})
	private Picture frontPicture;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="pic", column=@Column(name="BACK_PIC")),
	})
	private Picture backPicture;


	protected PlayerCard() {
		super();
	}

	public PlayerCard(final Player p, final Picture frontPic, final Picture backPic, final LocalDateTime theDate) {
		super();
		player = p;
		frontPicture = frontPic;
		backPicture = backPic;
		date = theDate;
	}

	public void setAlbum(final Album album) {
		this.album = album;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("id", id)
			.add("player", player)
			.add("date", date)
			.add("frontPicture", frontPicture)
			.add("backPicture", backPicture)
			.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof PlayerCard)) {
			return false;
		}
		final PlayerCard that = (PlayerCard) o;
		return Objects.equals(album, that.album) && Objects.equals(player, that.player) && Objects.equals(date, that.date) &&
			Objects.equals(frontPicture, that.frontPicture) && Objects.equals(backPicture, that.backPicture);
	}

	@Override
	public int hashCode() {
		return Objects.hash(album, player, date, frontPicture, backPicture);
	}
}
