package com.rah.demo.googleauth.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
        http
            // 1. Permitir el acceso público a la consola de H2
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/login/**", "/oauth2/**", "/auth/**", "/api/mfa/**").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated() // El resto de la API sigue protegida
            )
            // 2. Desactivar CSRF solo para las peticiones de la consola de H2
            .csrf(csrf -> csrf
            	.ignoringRequestMatchers("/oauth2/**", "/auth/user", "/api/mfa/**")
                .ignoringRequestMatchers(PathRequest.toH2Console())
            )
            // 3. Habilitar el flujo de inicio de sesión (Indispensable para OAuth2 / Google)
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/auth/user", true) // Te redirige aquí tras loguearte
            )
            // 4. Permitir marcos (iframes) del mismo origen (necesario para la UI de H2)
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            );
		// @formatter:on

		return http.build();
	}
}
