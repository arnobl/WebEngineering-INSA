package fr.insarennes.demo.restcontroller;

import fr.insarennes.demo.model.Animal;
import fr.insarennes.demo.service.AnimalService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public/v1/animal")
public class AnimalController {
	private final AnimalService animalService;

	public AnimalController(final AnimalService service) {
		super();
		animalService = service;
	}

	@PostMapping(path = "",
		consumes = {MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE})
	public void newAnimal(@RequestBody final Animal animal) {
		animalService.getAnimals().add(animal);
	}

	@GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Animal> getAll() {
		return animalService.getAnimals();
	}
}
