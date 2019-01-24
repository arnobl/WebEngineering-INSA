package fr.insa.rennes.web.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is useless since PlayerCard can directly have a Image attribute.
 * This class is used to illustrate the Embeddable JPA relationship.
 * See PlayerCard
 */
@Embeddable
@XmlRootElement
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

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Picture)) {
			return false;
		}

		final Picture picture = (Picture) o;

		return Objects.equals(pic, picture.pic);
	}

	@Override
	public int hashCode() {
		return pic != null ? pic.hashCode() : 0;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("pic", pic).toString();
	}
}
