package fr.insa.rennes.web.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
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

	public void setTotalHomeRuns(final int totalHomeRuns) {
		this.totalHomeRuns = totalHomeRuns;
	}
}
