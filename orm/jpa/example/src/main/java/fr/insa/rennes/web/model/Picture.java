package fr.insa.rennes.web.model;

import javax.persistence.Embeddable;

/**
 * This class is useless since PlayerCard can directly have a Image attribute.
 * This class is used to illustrate the Embeddable JPA relationship.
 * See PlayerCard
 */
@Embeddable
public class Picture {
	private String pic;

	protected Picture() {
		super();
	}

	public Picture(final String img) {
		super();
		pic = img;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(final String pic) {
		this.pic = pic;
	}
}
