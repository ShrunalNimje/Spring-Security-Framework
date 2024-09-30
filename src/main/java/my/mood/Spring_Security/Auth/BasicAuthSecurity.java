package my.mood.Spring_Security.Auth;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration
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
		
		http.headers().frameOptions().sameOrigin();
		
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		var user = User.withUsername("Shrunal")
//			.password("{noop}dummy")
//			.roles("USER")
//			.build();
//		
//		var admin = User.withUsername("Admin")
//				.password("{noop}dummy")
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user, admin);
//		
//	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		
		var user = User.withUsername("Shrunal")
			// .password("{noop}dummy")
			.password("dummy")
			.passwordEncoder(pass -> passwordEncoder().encode(pass))
			.roles("USER", "ADMIN")
			.build();
		
		var admin = User.withUsername("Admin")
			// .password("{noop}dummy")
			.password("dummy")
			.passwordEncoder(pass -> passwordEncoder().encode(pass))
			.roles("ADMIN")
			.build();
		
		var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		
		return jdbcUserDetailsManager;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
