package tpspring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

record Message(String txt) {}


@RestController
@RequestMapping("api/v1/public/hello")
@CrossOrigin
public class HelloController {
	@GetMapping(path = "helloworld", produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "Hello World";
	}

	@GetMapping(path = "helloworld2", produces = MediaType.APPLICATION_JSON_VALUE)
	public Message helloWorld() {
		// Marshalling
		return new Message("Hello world!");
	}
}
