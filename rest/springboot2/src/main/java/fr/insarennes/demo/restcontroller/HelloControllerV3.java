package fr.insarennes.demo.restcontroller;

import fr.insarennes.demo.dto.UserDTO;
import fr.insarennes.demo.model.Message;
import fr.insarennes.demo.model.User;
import fr.insarennes.demo.service.DataService;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public/v3/hello")
public class HelloControllerV3 {
	private final DataService dataService;

	public HelloControllerV3(final DataService dataService) {
		super();
		this.dataService = dataService;
	}

	@PostMapping(path = "txt", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> newTxt(@RequestBody final Message txt) {
		if(dataService.getTxts().add(txt.text())) {
//			return new ResponseEntity<>(HttpStatus.OK);
			return ResponseEntity.ok().build();
		}
//		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(path = "world", produces = MediaType.APPLICATION_XML_VALUE)
	public Message helloWorld() {
		return new Message("Hello world!");
	}

	@GetMapping(path = "you", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> helloYou() {
//		return ResponseEntity.ok(new Message("Hello you!"));
		return new ResponseEntity<>(new Message("Hello you!"), HttpStatus.OK);
	}

	@GetMapping(path = "txts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<String>> getAllTxts() {
//		return new ResponseEntity<>(txts, HttpStatus.OK);
		return ResponseEntity.ok(dataService.getTxts());
	}

	@PutMapping(path = "user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> replaceUser(@RequestBody final User patchedUser) {
		if(patchedUser.getId().equals(dataService.getUser().getId())) {
			dataService.setUser(patchedUser);
			return ResponseEntity.ok().build();
		}
		throw new IllegalArgumentException(patchedUser.toString());
//		new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ID is not the same");
	}

	@GetMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO getUser() {
		return new UserDTO(dataService.getUser());
	}

	@PatchMapping(path = "user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void patchUser(@RequestBody final UserDTO patchedUser) {
		patchedUser.patch(dataService.getUser());
	}
}
