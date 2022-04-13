package fr.insarennes.demo.service;

import fr.insarennes.demo.model.Animal;
import fr.insarennes.demo.model.Cat;
import fr.insarennes.demo.model.Dog;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class AnimalService {
	private final List<Animal> animals;

	public AnimalService() {
		super();
		animals = new ArrayList<>();
		animals.add(new Cat(1, "foo"));
		animals.add(new Dog(2, "bar"));
	}
}
