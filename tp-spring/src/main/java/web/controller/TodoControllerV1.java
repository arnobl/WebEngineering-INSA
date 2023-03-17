package web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import web.model.Category;
import web.model.Todo;

@RestController
@RequestMapping("api/v1/public/todo")
@CrossOrigin
public class TodoControllerV1 {
	public TodoControllerV1() {
	}

	@GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "Hello";
	}
}
