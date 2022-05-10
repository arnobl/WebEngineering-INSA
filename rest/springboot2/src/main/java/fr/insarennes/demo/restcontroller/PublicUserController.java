package fr.insarennes.demo.restcontroller;

import fr.insarennes.demo.service.UserService;
import javax.servlet.ServletException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/public/user")
@AllArgsConstructor
public class PublicUserController {
	private final UserService userService;

	@PostMapping(value = "new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void newAccount(@RequestBody final UserDTO user) {
		try {
			userService.newAccount(user.login(), user.pwd());
		}catch(final IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not possible");
		}
	}

	@PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void login(@RequestBody final UserDTO user) {
		try {
			final boolean logged = userService.login(user.login(), user.pwd());

			if(logged) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already logged in. Log out first");
			}
		}catch(final ServletException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not possible to log in");
		}
	}
}


record UserDTO(String login, String pwd) {

}
