package fr.insarennes.demo.restcontroller;

import fr.insarennes.demo.model.User;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public/v1/hello")
public class HelloControllerV1 {
	private final Set<String> txts;
	private User user;

	public HelloControllerV1() {
		super();
		txts = new HashSet<>();
		txts.add("foo");
		txts.add("bar");
		user = new User("Foo", "here", "06", "p1");
	}


	@GetMapping(path = "world", produces = MediaType.TEXT_PLAIN_VALUE)
	public String helloWorld() {
		return "Hello world!";
	}

	@PostMapping(path = "txt", consumes = MediaType.TEXT_PLAIN_VALUE)
	public void newTxt(@RequestBody final String txt) {
		txts.add(txt);
	}

	@DeleteMapping(path = "txt", consumes = MediaType.TEXT_PLAIN_VALUE)
	public void removeTxt(@RequestBody final String txt) {
		txts.remove(txt);
	}

	@PatchMapping(path = "user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void patchUser(@RequestBody final User patchedUser) {
		patchedUser.patch(user);
	}

	@PutMapping(path = "user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void replaceUser(@RequestBody final User patchedUser) {
		if(user.getName().equals(patchedUser.getName())) {
			user = patchedUser;
		}
		else {
			// Is that a good practice to write user.toString()? (answer: no...)
			throw new IllegalArgumentException(user.toString());
		}
	}

	@PutMapping(path = "rename/{newname}")
	public void renameuser(@PathVariable("newname") final String newname) {
		user.setName(newname);
	}

	@GetMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser() {
		return user;
	}
}
