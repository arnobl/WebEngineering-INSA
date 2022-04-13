package fr.insarennes.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.insarennes.demo.model.Animal;
import fr.insarennes.demo.model.Cat;
import fr.insarennes.demo.model.Dog;
import fr.insarennes.demo.service.AnimalService;
import java.util.stream.Stream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private AnimalService animalService;

	@Test
	void getAll() throws Exception {
		// performing the query
		mvc.perform(get("/api/public/v1/animal/all"))
			// checking the status code
			.andExpect(status().isOk())
			// checking the returned data format
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			// printing in the console the returned data
			.andDo(MockMvcResultHandlers.print())
			// checking the JSON structure of the returned data
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].name", Matchers.equalTo("foo")))
			.andExpect(jsonPath("$[1].name", equalTo("bar")))
			.andExpect(jsonPath("$[0].type", equalTo("cat")))
			.andExpect(jsonPath("$[1].type", equalTo("dog")))
			.andExpect(jsonPath("$[0].age", is(1)))
			.andExpect(jsonPath("$[1].age", is(2)));
	}

	static Stream<Animal> animalsProvider() {
		return Stream.of(
			new Cat(20, "c"),
			new Dog(30, "d")
		);
	}

	static Stream<Arguments> postAnimalProvider() {
		return animalsProvider()
			.map(animal ->
				Stream.of(
					new Pair<>(MediaType.APPLICATION_JSON_VALUE, new ObjectMapper()),
					new Pair<>(MediaType.APPLICATION_XML_VALUE, new XmlMapper())
				).map(type -> Arguments.of(animal, type.first(), type.second())))
			.flatMap(s -> s);
	}

	@ParameterizedTest
	@MethodSource("postAnimalProvider")
	void postAnimal(final Animal animal, final String type, final ObjectMapper marshaller) throws Exception {
		mvc.perform(
				post("/api/public/v1/animal")
					.contentType(type)
					.content(marshaller.writeValueAsString(animal))
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().string(""));

		assertEquals(animalService.getAnimals().get(animalService.getAnimals().size() - 1), animal);
	}

	@Test
	void postBadAnimalNoType() throws Exception {
		mvc.perform(
				post("/api/public/v1/animal")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
						{ "age":2,"name":"bar" }""")
			)
			.andExpect(status().isBadRequest())
			.andExpect(content().string(""));
	}
}


record Pair<A, B>(A first, B second) {}


