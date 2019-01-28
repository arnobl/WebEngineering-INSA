package fr.insarennes.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

public abstract class CalendarElement {
	@ApiModelProperty(hidden = true) // This attribute (primary key) will be ignored by Swagger
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof CalendarElement)) {
			return false;
		}
		final CalendarElement that = (CalendarElement) o;
		return getId() == that.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
