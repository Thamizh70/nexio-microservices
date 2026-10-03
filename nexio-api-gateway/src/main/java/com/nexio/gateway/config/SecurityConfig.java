package com.nexio.gateway.config;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	/*
	 * Temporary development secret.
	 *
	 * IMPORTANT: Move this to environment variables / secret management before
	 * production.
	 */
	private static final String SECRET_KEY = "nexio-super-secret-key-change-this-in-production-2026";

	/**
	 * Spring Security WebFlux configuration.
	 */
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

		return http
				// REST API / JWT based application
				.csrf(csrf -> csrf.disable())

				// Authorization rules
				.authorizeExchange(exchange -> exchange

						// Public authentication endpoints
						.pathMatchers("/api/v1/auth/**").permitAll()

						// Health check
						.pathMatchers("/actuator/health").permitAll()

						// Everything else requires JWT
						.anyExchange().authenticated())

				// JWT authentication
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
				}))

				.build();
	}

	/**
	 * Reactive JWT decoder.
	 *
	 * Temporary HMAC configuration for local development.
	 */
	@Bean
	public ReactiveJwtDecoder jwtDecoder() {

		SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

		return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
	}
}