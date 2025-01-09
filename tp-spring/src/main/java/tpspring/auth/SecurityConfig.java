package tpspring.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		return http
			.authorizeHttpRequests(authz -> authz
				.requestMatchers("/api/v*/public/**").permitAll()
				.requestMatchers("/api/v*/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v*/private/**").hasRole("USER")
				.requestMatchers("/actuator/**").permitAll()
				.anyRequest().permitAll()
					// .anyRequest().authenticated();
			)
			.csrf(config -> config.disable())
			.build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
