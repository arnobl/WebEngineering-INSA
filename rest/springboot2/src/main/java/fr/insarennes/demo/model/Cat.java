package fr.insarennes.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true)
@Getter
@Setter
public class Cat extends AnimalBase {
	@JsonIgnore
	private int notToMarshall;

	public Cat(final int age, final String name) {
		super(age, name);
		notToMarshall = 1;
	}
}
