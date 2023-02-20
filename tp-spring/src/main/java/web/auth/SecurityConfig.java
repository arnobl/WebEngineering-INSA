package web.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(req -> {
				try {
					req.requestMatchers(
						new AntPathRequestMatcher("/api/**")).permitAll()
						.anyRequest().authenticated()
						.and()
						.csrf().disable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

		return http.build();
	}
}
