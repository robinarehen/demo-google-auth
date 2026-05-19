package com.rah.demo.googleauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rah.demo.googleauth.dto.UserDto;
import com.rah.demo.googleauth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@GetMapping("/user")
	public ResponseEntity<UserDto> getUserProfile(@AuthenticationPrincipal OAuth2User oAuth2User) {
		UserDto userDto = authService.processOAuth2User(oAuth2User);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/multi-user")
	public ResponseEntity<UserDto> getUserProfile(OAuth2AuthenticationToken authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).build();
		}

		// Obtenemos el proveedor (google o github)
		String provider = authentication.getAuthorizedClientRegistrationId();

		// Obtenemos el usuario autenticado
		var oAuth2User = authentication.getPrincipal();

		UserDto userDto = authService.processOAuth2User(oAuth2User, provider);
		return ResponseEntity.ok(userDto);
	}
}
