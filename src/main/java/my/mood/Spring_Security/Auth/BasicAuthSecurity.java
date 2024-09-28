package my.mood.Spring_Security.Auth;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthSecurity {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> {
					auth.anyRequest().authenticated();
				});
		
		http.sessionManagement(
				session -> {
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				});
		
		// http.formLogin(withDefaults());
		
		http.httpBasic(withDefaults());
		
		http.csrf().disable();
		
		return http.build();
	}
}
