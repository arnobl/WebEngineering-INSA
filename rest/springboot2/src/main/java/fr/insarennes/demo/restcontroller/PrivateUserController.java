package fr.insarennes.demo.restcontroller;

import fr.insarennes.demo.model.User2;
import fr.insarennes.demo.service.UserService;
import java.security.Principal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v2/private/user")
@AllArgsConstructor
@CrossOrigin
public class PrivateUserController {
	private final UserService userService;

	@GetMapping()
	public String hello(final Principal user) {
		return user.getName();
	}

	@DeleteMapping()
	public void delAccount(final Principal principal, final HttpServletRequest request) {
		if(principal == null) {
			return;
		}
		userService.delAccount(principal.getName());
		// Logging out the current user
		SecurityContextHolder.clearContext();
		final HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
	}

	@PostMapping("out")
	public void logout() {
		try {
			if(!userService.logout()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot log out");
			}
		}catch(final ServletException | IllegalStateException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot log out");
		}
	}


	// Principal is an object, Spring manages, that contains information about the currently logged-in user
	// The user is identified by Spring thanks to the jsession token.
	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void patchUser(final Principal principal, @RequestBody final UserInfoDTO userDTO) {
		final User2 user = userService.findUser(principal.getName());

		if(user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find the user");
		}

		userDTO.patch(user);
	}
}



// This DTO is only used for modifying (patching) user's information
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class UserInfoDTO {
	// We just want to modify these parameters
	/** The address */
	private String ad;
	/** The phone */
	private String ph;

	public void patch(final User2 user) {
		if(ad != null) {
			user.setAddress(ad);
		}
		if(ph != null) {
			user.setPhone(ph);
		}
	}
}
