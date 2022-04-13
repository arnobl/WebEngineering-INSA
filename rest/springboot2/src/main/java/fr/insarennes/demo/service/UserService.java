package fr.insarennes.demo.service;

import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
	private final PasswordEncoder passwordEncoder;

	private final UserDetailsManager userDetailsManager;

	private final HttpServletRequest request;


	/**
	 * @throws IllegalArgumentException If already exists
	 */
	public void newAccount(final String login, final String pwd) {
		if(userDetailsManager.userExists(login)) {
			throw new IllegalArgumentException("Not possible");
		}

		final UserDetails user = new User(login, passwordEncoder.encode(pwd),
			Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
		userDetailsManager.createUser(user);
	}

	public void delAccount(final String name) {
		userDetailsManager.deleteUser(name);
	}

	public boolean login(final String login, final String pwd) throws ServletException {
		final HttpSession session = request.getSession(false);

		if(session == null) {
			request.getSession(true);
			request.login(login, pwd);
			return true;
		}
		return false;
	}


	public boolean logout() throws ServletException {
		final HttpSession session = request.getSession(false);

		if(session == null) {
			return false;
		}
		request.logout();
		return true;
	}
}
