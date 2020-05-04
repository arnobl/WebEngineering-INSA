package fr.insarennes.model;

import io.swagger.annotations.ApiModelProperty;

public abstract class CalendarElement {
	static int ID_CPT = 0;

	@ApiModelProperty(hidden = true) // This attribute (primary key) will be ignored by Swagger
	protected int id;

	CalendarElement() {
		super();
		this.id = ID_CPT;
		ID_CPT++;
	}

	public int getId() {
		return id;
	}

//	@Override
//	public boolean equals(final Object o) {
//		if(this == o) {
//			return true;
//		}
//		if(!(o instanceof CalendarElement)) {
//			return false;
//		}
//		final CalendarElement that = (CalendarElement) o;
//		return getId() == that.getId();
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(getId());
//	}
}
