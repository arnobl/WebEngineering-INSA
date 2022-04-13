package fr.insarennes.demo.restcontroller;

import fr.insarennes.demo.model.Message;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public/v2/hello")
public class HelloControllerV2 {
	private final Set<String> txts;

	public HelloControllerV2() {
		super();
		txts = new HashSet<>();
		txts.add("foo");
		txts.add("bar");
	}

	@PostMapping(path = "txt", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void newTxt(@RequestBody final Message txt) {
		txts.add(txt.text());
	}

	@GetMapping(path = "world", produces = MediaType.APPLICATION_XML_VALUE)
	public Message helloWorld() {
		return new Message("Hello world!");
	}
}
