package fr.insa.rennes.web.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseballPlayer extends Player {
	private int totalHomeRuns;

	@Basic(optional = false)
	@Column(name = "POSITION", nullable = false)
	private Position position;


	protected BaseballPlayer() {
		super();
	}

	public BaseballPlayer(final String name, final Position playerPosition, final int totalHomeRuns) {
		super(name);
		this.totalHomeRuns = totalHomeRuns;
		position = playerPosition;
	}

	public int getTotalHomeRuns() {
		return totalHomeRuns;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof BaseballPlayer)) {
			return false;
		}
		if(!super.equals(o)) {
			return false;
		}
		final BaseballPlayer that = (BaseballPlayer) o;
		return getTotalHomeRuns() == that.getTotalHomeRuns() && getPosition() == that.getPosition();
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getTotalHomeRuns(), getPosition());
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("totalHomeRuns", totalHomeRuns)
			.add("position", position)
			.add("name", name)
			.add("id", id)
			.toString();
	}
}
