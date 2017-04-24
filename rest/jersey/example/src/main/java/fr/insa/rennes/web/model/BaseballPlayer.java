package fr.insa.rennes.web.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class BaseballPlayer extends Player {
	private int totalHomeRuns;

	protected BaseballPlayer() {
		super();
	}

	public BaseballPlayer(final String name, final int totalHomeRuns) {
		super(name);
		this.totalHomeRuns = totalHomeRuns;
	}

	public int getTotalHomeRuns() {
		return totalHomeRuns;
	}

	public void setTotalHomeRuns(final int totalHomeRuns) {
		this.totalHomeRuns = totalHomeRuns;
	}

	@Override
	public String toString() {
		return "BaseballPlayer{" + "id=" + id + ", name='" + name + '\'' + ", totalHomeRuns=" + totalHomeRuns + '}';
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) return true;
		if(!(o instanceof BaseballPlayer)) return false;
		if(!super.equals(o)) return false;

		BaseballPlayer that = (BaseballPlayer) o;

		return totalHomeRuns == that.totalHomeRuns;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + totalHomeRuns;
		return result;
	}
}
