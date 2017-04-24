package fr.insa.rennes.web.model;

import javax.persistence.Entity;

@Entity
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
}
