package co.com.telefonica.ws.util;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * NO BORRAR
 * Clase de implementaciones de Seguridad para el microservicio.
 *
 * @version 1.1.0
 * @author COEArquitectura@telefonica.com
 * @since 22/11/2022
 **/
@Configuration
public class TelcoSecurityConfig {

	@Value("${controller.properties.base-path}")
	private String uriBasePattern;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@SneakyThrows
	public SecurityFilterChain filterChain(HttpSecurity http) {
		String uriPattern = "/" + this.uriBasePattern + "/**";
		http.csrf()
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
		return web -> web.ignoring().antMatchers(
				"/" + uriBasePattern + "/**",
				"/actuator/**",
				"/v2/api-docs/**",
				"/swagger-ui/**",
				"/h2-console/**"
		);
	}

}