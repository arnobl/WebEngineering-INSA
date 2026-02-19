package tpspring.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import tpspring.model.Category;
import tpspring.model.Todo;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestTodoControllerV2 {
	@Autowired
	private MockMvc mvc;

	// @MockitoBean
    // private TodoServiceV2 todoService;

	@Test
	@WithMockUser(value = "usertest")
	void testHello() throws Exception {
		mvc.perform(get("/api/v1/public/hello/helloworld"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
			.andExpect(content().string(equalTo("Hello World")));
	}

	// @Test
	// @WithMockUser(value = "usertest")
	// void getTodo() throws Exception {
	// 	Mockito.when(todoService.findTodo(1L))
	// 		.thenReturn(Optional.of(new Todo(1L, "t1", "desc", List.of(Category.LOW_PRIORITY), null, "foo")));

	// 	mvc.perform(get("/api/v2/public/todo/todo/1"))
	// 			.andExpect(status().isOk())
	// 			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	// 			.andExpect(jsonPath("$.title", equalTo("t1")))
	// 			.andExpect(jsonPath("$.description", equalTo("desc")))
	// 			.andExpect(jsonPath("$.categories", hasSize(1)))
	// 			.andExpect(jsonPath("$.categories[0]", equalTo("LOW_PRIORITY")))
	// 			.andExpect(jsonPath("$.type", equalTo("todo")))
	// 			.andExpect(jsonPath("$.owner", equalTo("foo")))
	// 			.andExpect(jsonPath("$.id", equalTo(1)))
	// 			.andExpect(jsonPath("$.*", hasSize(6)));
	// }
}
