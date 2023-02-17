package web.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.model.Category;
import web.model.Todo;
import web.model.User;

@RestController
@RequestMapping("api/v1/insa/todo")
public class TodoV1 {
	@GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "Hello";
	}

}
