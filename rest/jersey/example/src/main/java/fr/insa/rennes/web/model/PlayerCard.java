package fr.insa.rennes.web.model;

import java.time.LocalDate;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlRootElement
public class PlayerCard extends ModelElement {
//	@Id
//	@GeneratedValue
//	private int id; // The id is provided by the super class

	@ManyToOne
	private Album album;

	@ManyToOne
	@JoinColumn(name = "PC_PLAYER", nullable = false)
	private Player player;

//	@XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
	private LocalDate date;

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

	public PlayerCard(final Player p, final Picture frontPic, final LocalDate theDate) {
		super();
		player = p;
		frontPicture = frontPic;
		date = theDate;
	}

	public void setAlbum(final Album album) {
		this.album = album;
	}

	@Override
	public String toString() {
		return "PlayerCard{" + "id=" + id + ", player=" + player + ", date=" + date + ", frontPicture=" + frontPicture + ", backPicture="
			+ backPicture + '}';
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) return true;
		if(!(o instanceof PlayerCard)) return false;

		PlayerCard that = (PlayerCard) o;

		if(album != null ? !album.equals(that.album) : that.album != null) return false;
		if(player != null ? !player.equals(that.player) : that.player != null) return false;
		if(date != null ? !date.equals(that.date) : that.date != null) return false;
		if(frontPicture != null ? !frontPicture.equals(that.frontPicture) : that.frontPicture != null) return false;
		return backPicture != null ? backPicture.equals(that.backPicture) : that.backPicture == null;
	}

	@Override
	public int hashCode() {
		int result = album != null ? album.hashCode() : 0;
		result = 31 * result + (player != null ? player.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (frontPicture != null ? frontPicture.hashCode() : 0);
		result = 31 * result + (backPicture != null ? backPicture.hashCode() : 0);
		return result;
	}
}
