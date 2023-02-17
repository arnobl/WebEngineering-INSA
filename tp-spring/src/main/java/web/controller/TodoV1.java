package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import web.model.Category;
import web.model.Todo;

@RestController
@RequestMapping("api/v1/todo")
public class TodoV1 {
	public TodoV1() {
	}

	@GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "Hello";
	}

}
