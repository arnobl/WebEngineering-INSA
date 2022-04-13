package fr.insarennes.demo.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonSubTypes({
	@JsonSubTypes.Type(value = Cat.class, name = "cat"),
	@JsonSubTypes.Type(value = Dog.class, name = "dog")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface Animal {
	int getAge();

	void setAge(final int age);

	String getName();
}
