package fr.insarennes.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	@Lazy
//	UserDetailsManager userDetailsService;



//	@Override
////	@Bean
//	public UserDetailsManager userDetailsService() {
//		final var inMemoryUserDetailsManager = userDetailsManager();
//
//		final UserDetails testUser = User.withUsername("user")
//			.password(encoder().encode("user"))
//			.roles("USER")
//			.build();
////		final UserDetails testUser = User.withUsername("u1")
////			.password(encoder().encode("admin"))
////			.roles("ADMIN")
////			.build();
//		inMemoryUserDetailsManager.createUser(testUser);
//		return inMemoryUserDetailsManager;
//	}


	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsManager()).passwordEncoder(encoder());
//			.and()
//			.inMemoryAuthentication()
//			.withUser("admin")
//			.password(encoder().encode("admin"))
//			.roles("ADMIN")
//			.and()
//			.withUser("user")
//			.password(encoder().encode("user"))
//			.roles("USER");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
//			.antMatchers("/api/admin/**").hasRole("ADMIN")
			.antMatchers("/api/public/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.logout(logout -> logout.deleteCookies("JSESSIONID"))
			.csrf().disable();
//			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	public UserDetailsManager userDetailsManager() {
		return new InMemoryUserDetailsManager();
	}
}
