package fr.insa.rennes.web.model;

import java.time.LocalDate;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PlayerCard extends ModelElement {
//	@Id
//	@GeneratedValue
//	private int id; // The id is provided by the super class

	@ManyToOne
	private Album album;

	@ManyToOne
	@JoinColumn(name = "PC_PLAYER", nullable = false)
	private Player player;

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
}
