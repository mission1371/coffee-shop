package ee.coffeeshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
		return http.authorizeExchange().anyExchange().authenticated().and().httpBasic().and().csrf().disable().cors()
				.and().build();
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		return new MapReactiveUserDetailsService(user("salesman1"), user("salesman2"), admin());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private UserProfile user(final String username) {
		return new UserProfile(username, "$2a$10$NBlYMk6WMqEvistrC4I6PuzmanI8Um7HRQWsBRf5AUddfQBVaUKl2",
				List.of("ROLE_USER"));
	}

	private UserDetails admin() {
		return new UserProfile("admin", "$2a$10$Yhrxy2HpszeHnwW/B5PoSOzepTFQaiq9V1/v21NBNCMidq4RpBK3y",
				List.of("ROLE_USER", "ROLE_ADMIN"));
	}

}
