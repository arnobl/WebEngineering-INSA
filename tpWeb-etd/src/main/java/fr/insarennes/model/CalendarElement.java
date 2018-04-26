package fr.insarennes.model;

import io.swagger.annotations.ApiModelProperty;

public abstract class CalendarElement {
	@ApiModelProperty(hidden = true) // This attribute (primary key) will be ignored by Swagger
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
