package fr.insarennes.demo.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsManager userDetailsManager;

	@Autowired
	private HttpServletRequest request;

	/**
	 * @throws IllegalArgumentException If already exists
	 */
	public void newAccount(final String login, final String pwd) {
		if(userDetailsManager.userExists(login)) {
			throw new IllegalArgumentException("Not possible");
		}

		final UserDetails user = new User(login, passwordEncoder.encode(pwd), List.of(new SimpleGrantedAuthority("ROLE_USER")));
		userDetailsManager.createUser(user);
	}

	public void delAccount(final String name) {
		// warning: IRL, should remove associated data as well
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

	public UserDetails findUser(final String userName) {
		return userDetailsManager.loadUserByUsername(userName);
	}
}

