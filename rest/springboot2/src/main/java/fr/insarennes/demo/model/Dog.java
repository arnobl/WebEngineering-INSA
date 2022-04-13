package fr.insarennes.demo.model;

import lombok.ToString;

@ToString(callSuper=true)
public class Dog extends AnimalBase {
	public Dog(final int age, final String name) {
		super(age, name);
	}
}
