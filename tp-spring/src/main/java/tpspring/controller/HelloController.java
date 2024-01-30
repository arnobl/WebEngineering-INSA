package tpspring.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

record Message(String txt) {}


@RestController
@RequestMapping("api/v1/public/hello")
@CrossOrigin
public class HelloController {
	private final List<String> txts = new ArrayList<>();

	@GetMapping(path = "helloworld", produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "Hello World";
	}

	@GetMapping(path = "helloworld2", produces = MediaType.APPLICATION_JSON_VALUE)
	public Message helloWorld() {
		// Marshalling
		return new Message("Hello world!");
	}

    @PostMapping(path = "txt", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void newTxt(@RequestBody final String txt) {
        txts.add(txt);
    }

    @GetMapping(path = "txt", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTxts() {
        return txts;
    }
}
