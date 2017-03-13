package fr.insa.rennes.web.model;

import java.time.LocalDate;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PlayerCard {
	@Id
	@GeneratedValue
	private int id; // Should factorise this code in a super abstract class. See: the with inheritance example

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

	public PlayerCard() {
		super();
	}

	public PlayerCard(final Player p, final Picture frontPic, final LocalDate theDate) {
		super();
		player = p;
		frontPicture = frontPic;
		date = theDate;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(final Album album) {
		this.album = album;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public Picture getFrontPicture() {
		return frontPicture;
	}

	public void setFrontPicture(final Picture picture) {
		this.frontPicture = picture;
	}

	public Picture getBackPicture() {
		return backPicture;
	}

	public void setBackPicture(final Picture backPicture) {
		this.backPicture = backPicture;
	}
}
