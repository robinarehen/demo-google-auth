package com.rah.demo.googleauth.service;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.rah.demo.googleauth.dto.UserDto;

@Service
public class AuthService {

	public UserDto processOAuth2User(OAuth2User oAuth2User) {
		if (oAuth2User == null) {
			return null;
		}

		// Google devuelve los datos en un Map de atributos
		String name = oAuth2User.getAttribute("name");
		String email = oAuth2User.getAttribute("email");
		String picture = oAuth2User.getAttribute("picture");

		return new UserDto(name, email, picture, "Google-Auth-1");
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

		return new UserDto(name, email, picture, "Google-Auth-1");
	}
}
