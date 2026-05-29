package com.rah.demo.googleauth.service;

import java.net.URI;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rah.demo.googleauth.dto.MfaStatus;
import com.rah.demo.googleauth.dto.UserDto;
import com.rah.demo.googleauth.entity.UserDataEntity;
import com.rah.demo.googleauth.repository.UserDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	private final UserDataRepository userDataRepository;
	private final TotpService totpService;
	private final RestClient restClient;

	public String generateToken(String email, String name) {
		// @formatter:off
		return JWT.create()
				.withSubject(email)
				.withClaim("name", name)
				.withIssuedAt(Instant.now())
				.withExpiresAt(Instant.now().plusMillis(this.expiration))
				.sign(Algorithm.HMAC256(secret));
		// @formatter:on
	}

	public UserDto processOAuth2User(OAuth2User oAuth2User) {
		if (oAuth2User == null) {
			return null;
		}

		// Google devuelve los datos en un Map de atributos
		String name = oAuth2User.getAttribute("name");
		String email = oAuth2User.getAttribute("email");
		String picture = oAuth2User.getAttribute("picture");

		return new UserDto(name, email, picture, "Google-Auth-1", this.generateToken(email, name));
	}

	public UserDto processOAuth2User(OAuth2User oAuth2User, String clientRegistrationId) {
		if (oAuth2User == null) {
			return null;
		}

		String name;
		String email;
		String picture;

		// Normalizamos los datos dependiendo de la plataforma de origen
		if ("github".equalsIgnoreCase(clientRegistrationId)) {
			name = oAuth2User.getAttribute("name");
			// Si el nombre público está vacío, usamos el login (username) de GitHub
			if (name == null) {
				name = oAuth2User.getAttribute("login");
			}
			email = oAuth2User.getAttribute("email");
			picture = oAuth2User.getAttribute("avatar_url"); // Atributo específico de GitHub
		} else {
			// Por defecto asumimos Google u otros estándar
			name = oAuth2User.getAttribute("name");
			email = oAuth2User.getAttribute("email");
			picture = oAuth2User.getAttribute("picture");
		}

		return new UserDto(name, email, picture, "Google-Auth-1", this.generateToken(email, name));
	}

	public String enviarMfaQr(UserDto userDto) {

		Optional<UserDataEntity> userDataDb = this.userDataRepository.findByUserEmail(userDto.email());

		String message = "Se ha enviado un Correo con indicaciones para activar el segundo factor. a:"
				.concat(userDto.email());

		return userDataDb.filter(userData -> MfaStatus.PENDING.name().equalsIgnoreCase(userData.getMfaStatus()))
				.map(userData -> message).orElseGet(() ->

				userDataDb.filter(userData -> MfaStatus.ACTIVATED.name().equalsIgnoreCase(userData.getMfaStatus()))
						.map(userData -> "Mfa Activado").orElse(this.enviarEmail(userDto))

				);
	}

	private String enviarEmail(UserDto userDto) {
		byte[] qrImage = this.totpService.generateQrCodeImage(userDto.email(), 250, 250);
		String strQrImage = Base64.getEncoder().encodeToString(qrImage);
		return this.restClient.post().uri(URI.create("http://localhost:8082/enviar-mfa-qr"))
				.body(Map.of("destino", userDto.email(), "asunto", "Activar MFA", "qrImage", strQrImage)).retrieve()
				.body(String.class);
	}
}
