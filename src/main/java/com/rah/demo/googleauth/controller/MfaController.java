package com.rah.demo.googleauth.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rah.demo.googleauth.dto.SetupResponse;
import com.rah.demo.googleauth.dto.VerificationRequest;
import com.rah.demo.googleauth.dto.VerificationResponse;
import com.rah.demo.googleauth.service.TotpService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/mfa")
public class MfaController {

	private final TotpService totpService;

	public MfaController(TotpService totpService) {
		super();
		this.totpService = totpService;
	}

	/**
	 * Retornar los siguientes valores
	 * 
	 * La URL: con la que se puede generar el QR con alguna pagina online
	 * 
	 * El SecretKey: el cual se puede pasar a la aplicación que genera el código
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/setup")
	public ResponseEntity<SetupResponse> setupMfa(@RequestParam @NotBlank @Email String email) {
		return ResponseEntity.ok(this.totpService.getQrUrl(email));
	}

	@PostMapping("/verify")
	public ResponseEntity<VerificationResponse> verifyMfa(@Valid @RequestBody VerificationRequest request) {
		VerificationResponse response = this.totpService.verifyCode(request);
		return ResponseEntity.status(response.status()).body(response);
	}

	@GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getMfaQrCode(@RequestParam @NotBlank @Email String email) {
		byte[] qrImage = this.totpService.generateQrCodeImage(email, 250, 250);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
	}
}
