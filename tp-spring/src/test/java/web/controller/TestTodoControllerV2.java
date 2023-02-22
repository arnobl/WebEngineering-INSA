package web.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestTodoControllerV2 {
	@Autowired
	private MockMvc mvc;

	@Test
	void testHello() throws Exception {
		mvc.perform(get("/api/v2/public/todo/hello"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
			.andExpect(content().string(equalTo("Hello")));
	}

	@Test
	void getTodo() throws Exception {
		mvc.perform(get("/api/v2/public/todo/todo"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", equalTo("A title")))
				.andExpect(jsonPath("$.description", equalTo("desc")))
				.andExpect(jsonPath("$.categories", hasSize(2)))
				.andExpect(jsonPath("$.categories[0]", equalTo("ENTERTAINMENT")))
				.andExpect(jsonPath("$.categories[1]", equalTo("WORK")))
				.andExpect(jsonPath("$.type", equalTo("todo")))
				.andExpect(jsonPath("$.owner", equalTo("foo")))
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.*", hasSize(6)));
	}

	@Test
	void postTodo() throws Exception {
		mvc.perform(post("/api/v2/public/todo/todo")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("""
					{
						"type": "todo",
						"title": "VVVV",
						"description": "desc",
						"categories": [
						  "ENTERTAINMENT"
						],
						"owner": "foo"
					  }
											"""))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", equalTo("VVVV")))
				.andExpect(jsonPath("$.description", equalTo("desc")))
				.andExpect(jsonPath("$.categories", hasSize(1)))
				.andExpect(jsonPath("$.categories[0]", equalTo("ENTERTAINMENT")))
				.andExpect(jsonPath("$.type", equalTo("todo")))
				.andExpect(jsonPath("$.owner", equalTo("foo")))
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.*", hasSize(6)));

	}
}
