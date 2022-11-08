package co.com.telefonica.ws.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

	@Value("${controller.properties.base-path}")
	private String uriBasePattern;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		String uriPattern = "/" + this.uriBasePattern + "/**";

		http
				.csrf()
				.and()
				.authorizeRequests()
				.antMatchers(uriPattern).authenticated()
				.and()
				.headers()
				.xssProtection()
				.and()
				.frameOptions()
				.sameOrigin()
				.xssProtection()
				.and()
				.contentSecurityPolicy("script-src 'self'");

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/" + uriBasePattern + "/**", "/v2/api-docs/**", "/swagger-ui/**", "/h2-console/**", "/actuator/**");
	}

}