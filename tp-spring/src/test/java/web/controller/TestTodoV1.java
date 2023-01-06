package web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestTodoV1 {
	@Autowired
	private MockMvc mvc;


	@Test
	void getTodo() throws Exception {
		mvc.perform(get("/api/v1/insa/todo/todo"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title", equalTo("t1")))
			.andExpect(jsonPath("$.privateDescription", equalTo("foo")))
			.andExpect(jsonPath("$.publicDescription", equalTo("bar")))
			.andExpect(jsonPath("$.categories", hasSize(1)))
			.andExpect(jsonPath("$.categories[0]", equalTo("ENTERTAINEMENT")))
			.andExpect(jsonPath("$.*", hasSize(4)));
	}

	@Test
	void postTodo() throws Exception {
		mvc.perform(post("/api/v1/insa/todo/todo")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("""
{
    "title": "title2",
    "privateDescription": "foo",
    "publicDescription": "bar,
    "categories": ["ENTERTAINEMENT"]
}
					"""))
			.andExpect(status().isOk())
			.andExpect(content().string(""));

	}
}
