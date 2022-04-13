package fr.insarennes.demo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
abstract class AnimalBase implements Animal {
	protected int age;
	protected String name;

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(final int age) {
		this.age = age;
	}

	@Override
	public String getName() {
		return name;
	}
}
